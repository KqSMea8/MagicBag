package org.github.helixcs.netty.chapter4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 1/26/2019.
 * @Desc:
 */
public class ClientRegisterHandler extends ChannelInboundHandlerAdapter {
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientRegisterHandler(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //  client register
        ctx.writeAndFlush(clientId);
        super.channelActive(ctx);

    }
}
