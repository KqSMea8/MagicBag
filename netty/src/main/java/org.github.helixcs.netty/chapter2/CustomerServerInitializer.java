package org.github.helixcs.netty.chapter2;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public class CustomerServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new CustomerEncoder());
        ch.pipeline().addLast(new CustomerDecoder());
        ch.pipeline().addLast(new ServerHandler());
    }
}
