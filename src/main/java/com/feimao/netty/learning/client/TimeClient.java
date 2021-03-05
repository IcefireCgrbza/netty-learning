package com.feimao.netty.learning.client;

import com.feimao.netty.learning.handler.TimeClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: feimao
 * @Date: 19-5-13 下午9:12
 */
public class TimeClient {

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup worderGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(worderGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture f = b.connect("127.0.0.1", 8083).sync();
            f.channel().closeFuture().sync();
        } finally {
            worderGroup.shutdownGracefully();
        }
    }
}
