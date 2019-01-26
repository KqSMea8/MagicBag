package org.github.helixcs.netty.chapter4;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import static io.netty.handler.timeout.IdleState.WRITER_IDLE;

public class Client {

    private String host;

    private int port;

    private String clientId;

    private Bootstrap bootstrap;

    private ChannelFuture channelFuture;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(Bootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }

    public Client(String host, int port, String clientId) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        doConnection(host, port);
    }

    public Client(String host, int port, Bootstrap bootstrap, ChannelFuture channelFuture) {
        this.host = host;
        this.port = port;
        this.bootstrap = bootstrap;
        this.channelFuture = channelFuture;
    }

    private static class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("> client 循环心跳检查:" + new Date());
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent)evt;
                if (event.state() == WRITER_IDLE) {
                    ctx.writeAndFlush("hohoho");
                }
            }
        }
    }

    private void doConnection(String host, int port) {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workGroup)
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                    ch.pipeline().addLast(new StringEncoder());
                    ch.pipeline().addLast(new StringDecoder());
                    ch.pipeline().addLast(new HeartbeatHandler());
                    ch.pipeline().addLast(new ClientRegisterHandler(clientId));
                }
            });
        try {
            channelFuture = bootstrap.connect(host, port).sync();
            //future.channel().close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workGroup.shutdownGracefully();
        }

    }

}
