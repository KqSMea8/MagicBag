package org.github.helixcs.netty.chapter1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class Client {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new CustomerInitializer());

            ChannelFuture channelFuture = b.connect("localhost", 9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {

        } finally {
            group.shutdownGracefully();
        }
    }

    private static class CustomerInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024))
                    .addLast(new StringDecoder(CharsetUtil.UTF_8))
                    .addLast(new StringEncoder(CharsetUtil.UTF_8))
                    .addLast(new SimpleChannelInboundHandler() {
                        /**
                         * 当客户端和服务端TCP链路建立成功后， 会调用此方法
                         */
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            byte[] req = "Hello".getBytes();
                            ByteBuf byteBuf = Unpooled.buffer(req.length);
                            byteBuf.writeBytes(req);
                            ctx.writeAndFlush(byteBuf);
                        }


                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
                            String body = (String) msg;
                            System.out.println("Now is : " + body);
                        }

                    });
        }
    }


}
