package org.github.helixcs.netty.chapter4;

import io.netty.channel.ChannelFuture;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 1/27/2019.
 * @Desc:
 */
public class C4ServerInvoker  {

    private ChannelFuture serverChannelFuture;


    public void sendMsg(final String rqString) {
        if (null == serverChannelFuture) {
            System.out.println("> C4Server startSever before sendMsg!");
            return;
        }
        serverChannelFuture.channel().writeAndFlush(rqString);
    }

}
