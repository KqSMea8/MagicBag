package org.github.helixcs.netty.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.dns.*;

import java.util.HashMap;
import java.util.Map;

public class DnsServerHandler extends SimpleChannelInboundHandler<DatagramDnsQuery> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramDnsQuery msg) throws Exception {
        System.out.println("==> " + msg.content());
        // 假数据，域名和ip的对应关系应该放到数据库中
        Map<String, byte[]> ipMap = new HashMap<>();
        ipMap.put("www.baidu.com.", new byte[]{61, (byte) 135, (byte) 169, 125});

        DatagramDnsResponse response = new DatagramDnsResponse(msg.recipient(), msg.sender(), msg.id());
        DefaultDnsQuestion dnsQuestion = msg.recordAt(DnsSection.QUESTION);
        response.addRecord(DnsSection.QUESTION, dnsQuestion);
        System.out.println("查询的域名：" + dnsQuestion.name());

        ByteBuf buf ;
        if (ipMap.containsKey(dnsQuestion.name())) {
            buf = Unpooled.wrappedBuffer(ipMap.get(dnsQuestion.name()));
        } else {
            // TODO  对于没有的域名采用迭代方式
             buf = Unpooled.wrappedBuffer(new byte[] { 127, 0, 0, 1});
        }
        // TTL设置为10s, 如果短时间内多次请求，客户端会使用本地缓存
        assert buf != null;
        DefaultDnsRawRecord queryAnswer = new DefaultDnsRawRecord(dnsQuestion.name(), DnsRecordType.A, 10, buf);
        response.addRecord(DnsSection.ANSWER, queryAnswer);
        ctx.writeAndFlush(response);
    }
}
