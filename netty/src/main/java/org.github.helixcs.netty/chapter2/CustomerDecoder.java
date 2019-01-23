package org.github.helixcs.netty.chapter2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

// 自定义解码器
public class CustomerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        int version = in.readInt();

        int contentLength  = in.readInt();

        byte[] sessionByte = new byte[36];
        in.readBytes(sessionByte);
        String sessionId = new String(sessionByte );

        CustomerProtocol.Header header = new CustomerProtocol.Header(version,contentLength,sessionId);


        // 读取消息内容
        byte[] content =  new byte[in.readableBytes()];
        in.readBytes(content);
        String contentString = new String(content);
        CustomerProtocol customerProtocol = new CustomerProtocol(header,contentString);
        out.add(customerProtocol);
    }
}
