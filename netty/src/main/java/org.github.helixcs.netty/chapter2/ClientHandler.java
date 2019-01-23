package org.github.helixcs.netty.chapter2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

public class ClientHandler extends SimpleChannelInboundHandler<CustomerProtocol> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("> Client actived");
        int version = 1;
        String sessionId = UUID.randomUUID().toString();
        String content = "I'm the luck protocol!";

        CustomerProtocol.Header header = new CustomerProtocol.Header(version, content.length(), sessionId);
        CustomerProtocol message = new CustomerProtocol(header, content);
        ctx.writeAndFlush(message);
        ctx.channel().close();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomerProtocol msg) throws Exception {
        System.out.println("> Client get :" + msg.toString());
    }
}
