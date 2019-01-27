package org.github.helixcs.netty.chapter4;

import java.net.InetSocketAddress;
import java.text.MessageFormat;
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
import lombok.Data;

import static io.netty.handler.timeout.IdleState.WRITER_IDLE;

@Data
public class C4Client {

    private String host;

    private int port;

    private String clientId;

    private Bootstrap bootstrap;

    private ChannelFuture channelFuture;

    public C4Client(String host, int port, String clientId) {
        this.host = host;
        this.port = port;
        this.clientId = clientId;
        doConnection(host, port);
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
                    ch.pipeline().addLast(new C4ClientHandler());
                }
            });
        try {
            ChannelFuture tempF = bootstrap.connect(host, port).sync();
            this.setChannelFuture(tempF);
            //channelFuture.channel().writeAndFlush("Hello World");
            //channelFuture.channel().writeAndFlush(Unpooled.copiedBuffer("Hello Netty".getBytes()));
            //future.channel().close();
        } catch (InterruptedException e) {
            e.printStackTrace();
            if (null != channelFuture) {
                channelFuture.channel().closeFuture();
            }
            workGroup.shutdownGracefully();
        } finally {
            //workGroup.shutdownGracefully();
        }

    }

    private static class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            System.out.println("> client 循环心跳检查:" + new Date());
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent)evt;
                if (event.state() == WRITER_IDLE) {
                    ctx.writeAndFlush("ping");
                }
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            InetSocketAddress address =(InetSocketAddress)ctx.channel().remoteAddress();
            String clientInfo = address.getHostName()+"_"+address.getHostString()+":"+address.getPort();
            System.out.println(MessageFormat.format("> C4Client Heartbeat get msg :{0} from {1}" , msg.toString(),clientInfo));
        }
    }

    public static void main(String[] args) throws InterruptedException {

        C4Client c4Client = new C4Client("127.0.0.1", 9999, "clientid");
        Thread.sleep(5000);
        c4Client.getChannelFuture().channel().writeAndFlush("hello,world");

    }

}
