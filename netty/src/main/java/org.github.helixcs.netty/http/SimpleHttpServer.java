package org.github.helixcs.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class SimpleHttpServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new HttpServerInitializer());
            ChannelFuture future = serverBootstrap.bind(9999).sync();
            future.channel().closeFuture().sync();

        } catch (Exception ignored) {
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }


    private static class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            //http协议的编解码使用的,是HttpRequestDecoder和HttpResponseEncoder处理器组合
            //HttpRequestDecoder http请求的解码
            //HttpResponseEncoder http请求的编码
            ch.pipeline().addLast("httpServerCodec", new HttpServerCodec());
            ch.pipeline().addLast("customerHttpHandler", new CustomerHttpHandler());
        }
    }

    private static class CustomerHttpHandler extends SimpleChannelInboundHandler {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            System.out.println("> http server start read.");
            if (msg instanceof HttpRequest) {
                HttpRequest request = (HttpRequest) msg;
                String uri = request.getUri();
                System.out.println("> http server uri=" + uri);
            }


            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf byteBufContent = content.content();
//                byte[] contentBytes = new byte[byteBufContent.readableBytes()];
//                byteBufContent.readBytes(contentBytes);
//                String contentString = new String(contentBytes, "UTF-8");
//                System.out.println("> http server content=" + contentString);
//
                String contentString = byteBufContent.toString(CharsetUtil.UTF_8);
                System.out.println("> http server content=" + contentString);

                byteBufContent.release();


                // response
                String helloNetty = "Hello Netty";
                ByteBuf responseByteBuf = Unpooled.wrappedBuffer(helloNetty.getBytes("UTF-8"));
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, responseByteBuf);
                response.headers()
                        .add("hello", "netty")
                        .add("xxxx", "xxxx");
                // channel 为线程安全，可以使用多线程写入
                ChannelFuture cf = ctx.writeAndFlush(response);
                // 监听写入事件
                cf.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) throws Exception {
                        if(future.isSuccess()){
                            System.out.println("> "+helloNetty +" write success !");
                        }else {
                            System.out.println("> "+helloNetty+ " write failed!"+ future.cause().getMessage());
                        }
                    }
                });
                ctx.channel().close();

            }
        }
    }
}
