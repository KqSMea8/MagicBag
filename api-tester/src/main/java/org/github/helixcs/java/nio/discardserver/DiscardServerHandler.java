package org.github.helixcs.java.nio.discardserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.io.UnsupportedEncodingException;

/**
 * @Author: helix
 * @Time:9/18/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("> 服务端连接成功");
        ctx.writeAndFlush(Unpooled.copiedBuffer("服务端连接成功",CharsetUtil.UTF_8));
    }


        @Override
    public void channelRead(ChannelHandlerContext ctx,Object msg) throws UnsupportedEncodingException {
        System.out.println("> server 读取数据……");
        //读取数据
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("接收客户端数据:" + body);
        //向客户端写数据
        System.out.println(">server向client发送数据");
        ByteBuf resp = Unpooled.copiedBuffer("fuck".getBytes());
        ctx.write(resp);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx){
        System.out.println("server 读取数据完毕..");
        ctx.flush();//刷新后才将数据发出到SocketChannel

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();

    }
}
