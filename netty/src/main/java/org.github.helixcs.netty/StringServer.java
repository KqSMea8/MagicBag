package org.github.helixcs.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;

public class StringServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();

        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress("localhost", 9999)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                    super.channelActive(ctx);
//                                    ctx.
                                    System.out.println("> 服务端激活链接");
                                }

                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    ByteBuf byteBuf = (ByteBuf) msg;
                                    byte[] req = new byte[byteBuf.readableBytes()];
                                    byteBuf.readBytes(req);
                                    String reqString = new String(req, "UTF-8");
                                    System.out.println("> 服务端接受数据:" + reqString);
                                    String res = getRequest(reqString);
                                    System.out.println("> 服务端刷新数据:" + res);

                                    byte[] resByte = res.getBytes();
                                    ByteBuf wbf = Unpooled.buffer(resByte.length);
                                    wbf.writeBytes(resByte);
//                                    ctx.writeAndFlush(wbf);
                                    ctx.write(wbf);
                                }

                                @Override
                                public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//                                    super.channelReadComplete(ctx);
                                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                                            .addListener(ChannelFutureListener.CLOSE);
                                }

                                private String getRequest(String reqString) throws IOException {
                                    HttpClient client = new HttpClient();
                                    GetMethod getMethod = new GetMethod(reqString);
                                    client.executeMethod(getMethod);
                                    return getMethod.getResponseBodyAsString();
                                }
                            });
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (Exception ex) {
            bossGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}
