package com.feimao.netty.learning.jdk;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author: feimao
 * @Date: 20-1-27 下午2:39
 */
public class NioEchoClient {

    private static byte[] bb = new byte[10250000];

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        Selector selector = Selector.open();

        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);

        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

        int total = 0;

        while (true) {
            if (selector.select(1000) == 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                iterator.remove();

                if (selectionKey.isConnectable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    channel.finishConnect();
                    channel.write(ByteBuffer.wrap(bb));
                }

                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100000);
                    int readBytes;
                    while ((readBytes = channel.read(byteBuffer)) > 0) {
                        total += readBytes;
                        System.out.println(readBytes);
                        byteBuffer.clear();
                    }
                    if (readBytes < 0) {
                        System.out.println(total);
                        channel.close();
                        selector.close();
                        return;
                    }
                }
            }
        }
    }
}
