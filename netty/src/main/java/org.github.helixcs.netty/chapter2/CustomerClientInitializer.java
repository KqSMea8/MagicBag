package org.github.helixcs.netty.chapter2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class CustomerClientInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(new CustomerEncoder());
        ch.pipeline().addLast(new CustomerDecoder());
        ch.pipeline().addLast(new ClientHandler());
//        ch.pipeline().addLast(new ClientOutHandler());
    }
}
