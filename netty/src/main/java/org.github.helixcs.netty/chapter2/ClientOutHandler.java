package org.github.helixcs.netty.chapter2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class ClientOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (msg instanceof CustomerProtocol){
            System.out.println("> Client send :"+msg.toString());
        }
//        ctx.channel().close();
    }
}
