package org.github.helixcs.netty.chapter1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;


//  FixedLengthFrameDecoder 根据包长度拆分,处理半包问题

public class Server {
    public static void main(String[] args) {
        int port = 9999;
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new CustomerInitalizer());
            ChannelFuture future = serverBootstrap.bind("127.0.0.1", port).sync();
            System.out.println("netty server has start ...");
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
            // FixedLengthFrameDecoder
            //ch.pipeline().addLast(new FixedLengthFrameDecoder(3));

            // DelimiterBaseFrameDecoder 特定字符分隔
            //// 字符串编码和解码
            ByteBuf delimiter = Unpooled.copiedBuffer("|".getBytes());
            ch.pipeline().addLast("framer", new DelimiterBasedFrameDecoder(1024,delimiter));
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
                        reqString = new String(req, StandardCharsets.UTF_8);
                    }
                    // StringDecoder
                    if (msg instanceof String) {
                        reqString = (String) msg;
                        System.out.println("> send receive :"+reqString);
                    }
                    reqString =  reqString+ " | msg from netty sever \n";
                    System.out.println("> server send :"+reqString);
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
