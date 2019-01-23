package org.github.helixcs.netty.chapter2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

public class Client {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new CustomerClientInitializer())
        ;
        try {
            ChannelFuture future = b.connect("127.0.0.1", 9998).sync();
            int version = 1;
            String sessionId= UUID.randomUUID().toString();
            String content = "hefsaf";
            CustomerProtocol.Header header = new CustomerProtocol.Header(version,content.length(),sessionId);
            CustomerProtocol customerProtocol = new CustomerProtocol(header,content);
            future.channel().writeAndFlush(customerProtocol);
            future.channel().close();
        } catch (InterruptedException e) {
            e.printStackTrace();

        } finally {
            group.shutdownGracefully();
        }

    }
}
