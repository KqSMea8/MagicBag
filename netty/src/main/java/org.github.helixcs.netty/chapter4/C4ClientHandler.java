package org.github.helixcs.netty.chapter4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 1/27/2019.
 * @Desc:
 */
public class C4ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("> C4ClientHandler has actived");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // String
        if (msg instanceof String) {
            String msgString = (String)msg;
            System.out.println("> C4ClientHandler get msg:" + msgString);
        } else {
            super.channelRead(ctx, msg);
        }
    }
}
