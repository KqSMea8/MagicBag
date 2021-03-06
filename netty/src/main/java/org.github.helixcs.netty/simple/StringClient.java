package org.github.helixcs.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class StringClient {
    public static void main(String[] args) {
        EventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                super.channelActive(ctx);
                                String url = "https://www.alitrip.com/";
                                System.out.println("> 客户端发送:" + url);
                                byte[] urlByte = url.getBytes();
                                ByteBuf urlByteBuf = Unpooled.buffer(urlByte.length);
                                urlByteBuf.writeBytes(urlByte, 0, urlByte.length);
                                ctx.writeAndFlush(urlByteBuf);
                            }

//                            @Override
//                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = (ByteBuf) msg;
//                                byte[] byteBufs = new byte[byteBuf.readableBytes()];
//                                byteBuf.readBytes(byteBuf);
//                                String body = new String(byteBufs, "UTF-8");
//
//                                System.out.println("> 客户端接收:" + body);
//                                ctx.close();
////                                super.channelRead(ctx, msg);
//                            }

                            byte[] buff = new byte[0];

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {

                                while (msg.isReadable()) {
                                    byte[] _temp = new byte[msg.readableBytes()];
                                    msg.readBytes(_temp);
                                    int _old_length = buff.length;
                                    buff = new byte[_old_length + _temp.length];
                                    System.arraycopy(_temp, 0, buff, _old_length, _temp.length);
                                }
                                String responseString = new String(buff, "utf-8");
                                buff = new byte[0];
                                System.out.println("> 客户端接收: \n" + responseString);
//                                System.out.println("> 客户端接收:" + msg.toString(CharsetUtil.UTF_8));
                            }
                        });

                    }
                });

        ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 9999));
        try {
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {

        } finally {
            workGroup.shutdownGracefully();
        }

    }
}
