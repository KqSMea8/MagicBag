package org.github.helixcs.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpProxyServer {
    public static class HttpProxyClientHandle extends ChannelInboundHandlerAdapter {

        private Channel clientChannel;

        public HttpProxyClientHandle(Channel clientChannel) {
            this.clientChannel = clientChannel;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            FullHttpResponse response = (FullHttpResponse) msg;
            //修改http响应体返回至客户端
            response.content().writeBytes("<script>alert('xxx');</script>".getBytes());
            response.headers().add("test", "from proxy");
            response.headers().add("netty", "proxy");
            clientChannel.writeAndFlush(msg);
        }
    }

    public static class HttpProxyInitializer extends ChannelInitializer {

        private Channel clientChannel;

        public HttpProxyInitializer(Channel clientChannel) {
            this.clientChannel = clientChannel;
        }

        @Override
        protected void initChannel(Channel ch) throws Exception {
            ch.pipeline().addLast(new HttpClientCodec());
            ch.pipeline().addLast(new HttpObjectAggregator(6553600));
            ch.pipeline().addLast(new HttpProxyClientHandle(clientChannel));
        }
    }

    private static class HttpProxyServerHandle extends ChannelInboundHandlerAdapter {
        private ChannelFuture cf;
        private String host;
        private int port;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest request = (FullHttpRequest) msg;
                String host = request.headers().get("host");
                String[] temp = host.split(":");

                int port = 80;
                if (temp.length > 1) {
                    port = Integer.parseInt(temp[1]);
                } else {
                    if (request.uri().indexOf("https") == 0) {
                        port = 443;
                    }
                }
                this.host = temp[0];
                this.port = port;

                if ("CONNECT".equalsIgnoreCase(request.method().name())) {
                    HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.USE_PROXY);
                    ctx.writeAndFlush(response);
                    ctx.pipeline().remove("httpCodec");
                    ctx.pipeline().remove("httpObject");
                    return;
                }
                // 连接目标服务器
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(ctx.channel().eventLoop())
                        .channel(ctx.channel().getClass())
                        .handler(new HttpProxyInitializer(ctx.channel()));

                ChannelFuture cf = bootstrap.connect(temp[0], port);
                cf.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture future) {
                        if (future.isSuccess()) {
                            future.channel().writeAndFlush(msg);
                        } else {
                            cf.channel().close();
                        }
                    }
                });

            } else {
//                if (cf == null) {
//                    //连接至目标服务器
//                    Bootstrap bootstrap = new Bootstrap();
//                    bootstrap.group(ctx.channel().eventLoop()) // 复用客户端连接线程池
//                            .channel(ctx.channel().getClass()) // 使用NioSocketChannel来作为连接用的channel类
//                            .handler(new ChannelInitializer() {
//
//                                @Override
//                                protected void initChannel(Channel ch) throws Exception {
//                                    ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
//                                        @Override
//                                        public void channelRead(ChannelHandlerContext ctx0, Object msg) throws Exception {
//                                            ctx.channel().writeAndFlush(msg);
//                                        }
//                                    });
//                                }
//                            });
//                    cf = bootstrap.connect(host, port);
//                    cf.addListener(new ChannelFutureListener() {
//                        public void operationComplete(ChannelFuture future) throws Exception {
//                            if (future.isSuccess()) {
//                                future.channel().writeAndFlush(msg);
//                            } else {
//                                ctx.channel().close();
//                            }
//                        }
//                    });
//                } else {
//                    cf.channel().writeAndFlush(msg);
//                }
            }
        }
    }

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup(2);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast("httpCodec", new HttpServerCodec());
                            ch.pipeline().addLast("httpObject", new HttpObjectAggregator(65535));
                            ch.pipeline().addLast("serverHandle", new HttpProxyServerHandle());
                        }
                    });
            ChannelFuture f = bootstrap.bind(9999).sync();
            f.channel().closeFuture().sync();
        } catch (Exception ex) {

        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
