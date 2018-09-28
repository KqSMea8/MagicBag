package org.github.helixcs.java.nio.proxyserver;

import java.io.*;
import java.util.concurrent.CountDownLatch;

public class ProxyHandleThread  extends  Thread{
    private InputStream input;
    private OutputStream output;

    public ProxyHandleThread(InputStream input, OutputStream output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            while (true) {
                output.write(input.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
