package org.github.helixcs.java.nio.discardserver;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.github.helixcs.java.nio.SocketUtils.ETX;

/**
 * @Author: helix
 * @Time:9/19/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class DiscardClient {
    public static void main(String[] args) throws Exception {

        String host = "127.0.0.1";
        int port = 8099;
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DiscardClientHander());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(host,port));

            channelFuture.channel().closeFuture().sync();
        }finally {
            workGroup.shutdownGracefully();
        }
    }

    private void s1() throws Exception{
        Socket socket = new Socket("127.0.0.1",8099);
        if(!socket.isConnected()) {
            socket.connect(socket.getRemoteSocketAddress(), 3000);
        }
        InputStream in = socket.getInputStream();
        OutputStream ot = socket.getOutputStream();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ot));
        bw.write("hello world");
        bw.flush();

        StringBuilder sb= new StringBuilder();
        // 单个byte读取
        byte[] buffer = new byte[1];
        int hasRead;
        while ((hasRead = in.read(buffer)) != -1) {
            sb.append(in.read(buffer, 0, hasRead));
            // socket 结束标识
            if (buffer[0] == ETX) {
                break;
            }
        }
        System.out.println("==>"+sb.toString());

        in.close();
        ot.close();
        socket.close();
    }
}
