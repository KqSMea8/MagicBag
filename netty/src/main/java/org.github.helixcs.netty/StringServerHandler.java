package org.github.helixcs.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class StringServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        String reqString = new String(req, "UTF-8");
        System.out.println("> 接受数据" + reqString);
        String res = getRequest(reqString);
        ctx.writeAndFlush(res);
        ctx.close();
    }

    private String getRequest(String reqString) throws IOException {
        HttpClient client = new HttpClient();
        GetMethod getMethod = new GetMethod(reqString);
        client.executeMethod(getMethod);
        return getMethod.getResponseBodyAsString();
    }
}