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
//            channelFuture.channel().writeAndFlush("dakjbdsja|dasda|dsavfsa|daskda|dasjf|fabjsdba|fabsjda|");
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ignored) {

        } finally {
            group.shutdownGracefully();
        }
    }

    private static class CustomerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            // FixedLengthFrameDecoder
//            ch.pipeline().addLast("framer", new FixedLengthFrameDecoder(3));
            // DelimitedFrameDecoder
//            ByteBuf delimiter = Unpooled.copiedBuffer("\t".getBytes());
//            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(180,delimiter));
            // 字符串编码和解码
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new StringEncoder());
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                private static final String REQ = "LOUYYUTING netty. \t";

                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("zbbbvhgvhvhcgcgxfgxgfxgfxfdgxbzzzzz".getBytes()));
//                    String reqString = "dakjbdsja|dasda|dsavfsa|daskda|dasjf|fabjsdba|fabsjda";

                    for(int i=0;i<10;i++){
                        ctx.writeAndFlush(Unpooled.copiedBuffer(REQ.getBytes()));
                    }
                }

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    String res = "";
                    if (msg instanceof ByteBuf) {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        byte[] bytes = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(bytes);

                        res = new String(bytes, "UTF-8");
                    }


                    if (msg instanceof String) {
                        res = (String) msg;
                    }
                    System.out.println("Now is : " + res);
                }

            });
        }
    }


}
