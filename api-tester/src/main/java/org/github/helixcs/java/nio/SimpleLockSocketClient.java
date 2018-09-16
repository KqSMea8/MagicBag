package org.github.helixcs.java.nio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicReference;

import static org.github.helixcs.java.nio.SocketUtils.ETX;
import static org.github.helixcs.java.nio.SocketUtils.stringArrayToByteWithStartAndEnd;


/**
 * @Author: helix
 * @Time:9/17/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class SimpleLockSocketClient {

    // 防止多线程连接
    private final AtomicReference<HostAndPort> hostAndPortAtomicReference = new AtomicReference<>();

    private OutputStream ot;
    private InputStream in;
    private Socket socket;
    // 连接时间
    private int connectionTimeout = 30 * 1000;
    // 读取时间
    private int soTimeout = 20 * 1000;

    public SimpleLockSocketClient(String host, int port, int connectionTimeout, int soTimeout) throws IOException {
        this.connectionTimeout = connectionTimeout;
        this.soTimeout = soTimeout;
        this.hostAndPortAtomicReference.set(new HostAndPort(host, port));
        createSocket();
    }

    public SimpleLockSocketClient(String host, int port) throws IOException {
        this.hostAndPortAtomicReference.set(new HostAndPort(host, port));
        createSocket();
    }

    /**
     * 创建 socket
     * @throws IOException
     */
    private void createSocket() throws IOException {
        if (this.socket != null && !this.socket.isClosed()) { return; }
        final HostAndPort hostAndPort = hostAndPortAtomicReference.get();
        Socket socket = null;
        try {
            socket = new Socket(hostAndPort.getHost(), hostAndPort.getPort());
            if (!socket.isConnected()) {
                socket.connect(socket.getRemoteSocketAddress(), this.connectionTimeout);
            }
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(this.soTimeout);
            this.socket = socket;
        } catch (IOException ex) {
            System.out.println("> Socket 连接失败, host is :" + hostAndPort.getHost() + "; ip is :" + hostAndPort.getPort());
            if (socket != null) {
                if (socket.isConnected() || !socket.isClosed()) {
                    socket.close();
                }
            }
            throw ex;
        }
    }

    // HTTP 请求测试 socket ， 暂不使用
    public String sendHttpData(String... params) throws IOException {
        if(this.socket==null||socket.isClosed()){
            createSocket();
        }
        this.socket.setSoTimeout(30000);
        ot  = this.socket.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(ot);
        BufferedWriter writer = new BufferedWriter(streamWriter);
        for (String p:params){
            writer.write(p);
        }
        writer.flush();
        BufferedInputStream streamReader = new BufferedInputStream(socket.getInputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(streamReader, "utf-8"));

        StringBuilder stringBuilder = new StringBuilder();
        String buffer = null;
        while((buffer = reader.readLine())!= null){
            stringBuilder.append(buffer);
        }
        writer.close();
        reader.close();
        return stringBuilder.toString();
    }
    /**
     * 发送 lock socket
     * @param bytesParam        参数 byte 数组
     * @return                  响应 byte 数组
     * @throws IOException
     */
    public byte[] sendLockData(byte[] bytesParam) throws IOException {
        if (this.socket == null || socket.isClosed()) {
            createSocket();
        }
        ot = this.socket.getOutputStream();
        ot.write(bytesParam);
        ot.flush();
        in = this.socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 单个byte读取
        byte[] buffer = new byte[1];
        int hasRead;
        while ((hasRead = in.read(buffer)) != -1) {
            baos.write(buffer, 0, hasRead);
            // socket 结束标识
            if (buffer[0] == ETX) {
                break;
            }
        }
        close();
        return baos.toByteArray();
    }

    /**
     * 重载 sendLockData
     * @param arguments             参数数组
     * @return byte[]                byte 数组
     * @throws IOException
     */
    public byte[] sendLockData(String ... arguments) throws IOException {
        byte [] bytesParam = stringArrayToByteWithStartAndEnd(arguments);
        return sendLockData(bytesParam);
    }

    /**
     * 重载 sendLockData
     * @param stringByteArrayOutputStream   stringByteArrayOutputStream
     * @return                              byte 数组
     * @throws IOException
     */
    public byte[] sendLockData(StringByteArrayOutputStream stringByteArrayOutputStream) throws IOException {
        return sendLockData(stringByteArrayOutputStream.toByteArray());
    }

    /**
     * 重载 sendLockDataWithString 发送返回字符串响应
     * @param argument                      字符串数组
     * @return                              字符串
     * @throws IOException
     */
    public String sendLockDataWithString(String ... argument) throws IOException {
        return new String(sendLockData(argument));
    }

    /**
     * 重载 sendLockDataWithString    发送返回字符串响应
     * @param stringByteArrayOutputStream       stringByteArrayOutputStream
     * @return                                  字符串
     * @throws IOException
     */
    public String sendLockDataWithString(StringByteArrayOutputStream stringByteArrayOutputStream) throws IOException {
        return new String(sendLockData(stringByteArrayOutputStream));
    }
    /**
     * 关闭socket，流
     * @throws IOException
     */
    private void close() throws IOException {
        if (null!=socket &&  !socket.isClosed()) {
            socket.close();
        }
        if (null != in) {
            in.close();
        }
        if (null != ot) {
            ot.close();
        }

    }

    static class HostAndPort implements Serializable {
        private String host;

        private int port;

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public HostAndPort(String host, int port) {
            this.host = host;
            this.port = port;
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

}
