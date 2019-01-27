package org.github.helixcs.netty.chapter4;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.Getter;
import lombok.Setter;

public class C4Sever implements Runnable {

    @Getter
    @Setter
    private int port;

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup();

    private static final EventLoopGroup workGroup = new NioEventLoopGroup();

    @Getter
    @Setter
    private ChannelFuture channelFuture;

    public C4Sever(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        this.startServer0();
    }

    public void startServer() {
        Thread t = new Thread(new C4Sever(port));
        t.start();
    }

    private void startServer0() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .handler(new LoggingHandler(LogLevel.INFO))
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new HeartbeatHandler());
                    ch.pipeline().addLast(new C4ServerHandler());
                }
            });

        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        channelFuture = future;
                        System.out.println("> C4Sever startup success !");
                        return;
                    }
                    System.out.println("> C4Server startup failed!");

                }
            });
            System.out.println("> C4Server has start on " + port);
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        } finally {

        }

    }

    public void closeServer() {
        System.out.println("> C4Server stopping ....");
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    // heartbeat check
    static class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        private int lossConnectCount = 0;

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("> C4Server 已经5s 没有收到客户端消息");
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent)evt;
                if (event.state() == IdleState.READER_IDLE) {
                    lossConnectCount++;
                    if (lossConnectCount > 2) {
                        System.out.println("> C4Server 关闭这个不活跃的channel");
                        ctx.channel().close();
                    }
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            //reset
            lossConnectCount = 0;
            InetSocketAddress address = (InetSocketAddress)ctx.channel().remoteAddress();
            String clientInfo = address.getHostName() + "_" + address.getHostString() + ":" + address.getPort();
            System.out.println(
                MessageFormat.format("> C4Server Heartbeat get msg :{0} from {1}", msg.toString(), clientInfo));

            ctx.channel().writeAndFlush("pong");

        }
    }

    public static void main(String[] args) {
        C4Sever c4Sever = new C4Sever(9999);
        c4Sever.startServer0();
        c4Sever.getChannelFuture().channel().writeAndFlush("nihao");

    }
}
