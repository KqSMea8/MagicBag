package org.github.helixcs.netty.chapter1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Client {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new CustomerInitializer());

            ChannelFuture channelFuture = b.connect("127.0.0.1", 9999).sync();
//            channelFuture.channel().writeAndFlush(Unpooled.wrappedBuffer("dadadsa".getBytes()));
//            channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("zzzzzz".getBytes()));
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ignored) {

        } finally {
            group.shutdownGracefully();
        }
    }

    private static class CustomerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
//            ch.pipeline().addLast(new FixedLengthFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new StringEncoder());
            ch.pipeline().addLast(new SimpleChannelInboundHandler() {

                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    ctx.writeAndFlush(Unpooled.copiedBuffer("zbbbbzzzzz".getBytes()));
                }

                @Override
                protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                    String res = "";
                    if (msg instanceof ByteBuf) {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        byte[] bytes = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(bytes);

                        res = new String(bytes, "UTF-8");
                    }
                    if (msg instanceof String){
                        res = (String)msg;
                    }
                    System.out.println("Now is : " + res);
//                    ctx.channel().closeFuture().sync();
                }

            });
        }
    }


}
