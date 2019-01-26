package org.github.helixcs.netty.chapter3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.codec.dns.DatagramDnsQueryDecoder;
import io.netty.handler.codec.dns.DatagramDnsQueryEncoder;
import io.netty.handler.codec.dns.DatagramDnsResponseDecoder;
import io.netty.handler.codec.dns.DatagramDnsResponseEncoder;

public class DnsNettyServer {
    public static void main(String[] args) {

        EventLoopGroup workGroup = new NioEventLoopGroup(2);


        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel ch) throws Exception {
                        ch.pipeline().addLast(new DatagramDnsQueryDecoder());
                        ch.pipeline().addLast(new DatagramDnsQueryEncoder());
                        ch.pipeline().addLast(new DatagramDnsResponseDecoder());
                        ch.pipeline().addLast(new DatagramDnsResponseEncoder());
                        ch.pipeline().addLast(new DnsServerHandler());
                    }
                }).option(ChannelOption.SO_BROADCAST, true);
        try {
            ChannelFuture channelFuture = bootstrap.bind(53).sync();
            System.out.println("DNS Sever starting .....");
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }


    }
}
