package org.github.helixcs.netty.chapter1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new CustomerInitalizer());
            ChannelFuture future = serverBootstrap.bind("127.0.0.1", 9999).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private static class CustomerInitalizer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
//            ch.pipeline().addLast(new FixedLengthFrameDecoder(1024));
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new StringEncoder());
            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                @Override
                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                    System.out.println("> server has active");
                }

                @Override
                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                    String reqString = "";
                    if (msg instanceof ByteBuf) {
                        ByteBuf byteBuf = (ByteBuf) msg;
                        byte[] req = new byte[byteBuf.readableBytes()];
                        byteBuf.readBytes(req);
                        reqString = new String(req, "UTF-8");
                    }
                    if (msg instanceof String){
                        reqString = (String) msg;
                    }
                    ctx.write(Unpooled.wrappedBuffer(reqString.getBytes()));
                }

                @Override
                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                    ctx.channel().closeFuture().sync();
                }
            });
        }
    }
}
