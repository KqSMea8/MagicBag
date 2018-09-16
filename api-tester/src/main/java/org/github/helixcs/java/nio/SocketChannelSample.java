package org.github.helixcs.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Author: helix
 * @Time:9/16/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class SocketChannelSample {
    private Selector selector;
    private void initServer(int port) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.selector = Selector.open();


        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            if(socketChannel !=null) {
                System.out.println(socketChannel);
            }
        }
    }

    private static void testOpen() throws  Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

    }

    public static void main(String[] args) throws Exception {
        SocketChannelSample socketChannelSample= new SocketChannelSample();
        socketChannelSample.initServer(9999);
    }
}
