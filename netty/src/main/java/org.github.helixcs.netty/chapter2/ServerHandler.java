package org.github.helixcs.netty.chapter2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<CustomerProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("> Server actived");
    }

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof CustomerProtocol){
//            // 打印报文
//            System.out.println("> Server read:" + msg.toString());
//        }
//    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomerProtocol msg) throws Exception {
        // 打印报文
        System.out.println("> Server read:" + msg.toString());
    }

}
