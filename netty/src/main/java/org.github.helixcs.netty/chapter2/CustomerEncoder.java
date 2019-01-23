package org.github.helixcs.netty.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class CustomerEncoder extends MessageToByteEncoder<CustomerProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, CustomerProtocol msg, ByteBuf out) throws Exception {
        CustomerProtocol.Header header = msg.getHeader();

        // 按照顺序写入协议
        // 写入消息头
        out.writeInt(header.getVersion());
        out.writeInt(header.getLength());
        out.writeBytes(header.getSessionId().getBytes());

        // 写入消息内容
        out.writeBytes(msg.getContent().getBytes());


    }
}
