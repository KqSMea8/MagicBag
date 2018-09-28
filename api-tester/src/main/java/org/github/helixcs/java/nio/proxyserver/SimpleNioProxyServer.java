package org.github.helixcs.java.nio.proxyserver;

import java.io.IOException;
import java.net.ServerSocket;

public class SimpleNioProxyServer {
    public static void main(String[] args) throws IOException {
        int port = 9999;
        ServerSocket serverSocket = new ServerSocket(port);
        for (;;){
            new SimpleProxySocketHandler(serverSocket.accept()).start();
        }
    }
}
