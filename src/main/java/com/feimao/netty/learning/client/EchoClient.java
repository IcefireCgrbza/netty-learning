package com.feimao.netty.learning.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;
import java.nio.charset.Charset;

/**
 * @Author: feimao
 * @Date: 20-1-27 下午1:53
 */
public class EchoClient {

    public static void main(String[] args) {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buf = ctx.alloc().buffer();
                                buf.writeCharSequence("hello world", Charset.forName("UTF-8"));
                                ctx.writeAndFlush(buf).addListener(future -> {
                                    System.out.println("write future: " + future.isSuccess());
                                });
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(buf.readCharSequence(buf.readableBytes(), Charset.forName("UTF-8")));
                                ReferenceCountUtil.release(buf);
                                ctx.close().addListener(future -> {
                                    System.out.println("echo client close: " + future.isSuccess());
                                });
                            }
                        });
                    }
                });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8080).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
