package org.github.helixcs.java.nio.proxyserver;


import java.io.*;
import java.net.Socket;

public class SimpleProxySocketHandler extends Thread {

    private Socket socket;

    public SimpleProxySocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        OutputStream clientOt = null;
        InputStream clientIn = null;

        Socket proxySocket = null;
        OutputStream proxyOt = null;
        InputStream proxyIn = null;

        String host = "";

        try {
            clientIn = socket.getInputStream();
            clientOt = socket.getOutputStream();

            StringBuilder headStr = new StringBuilder();
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(clientIn));
            while ((line=br.readLine())!=null){
                System.out.println("==> " + line);
                headStr.append(line).append("\r\n");
                if(line.length()==0){
                    break;
                }else {
                    String[] tmp = line.split(" ");
                    if (tmp[0].contains("Host")){
                        host=tmp[1].trim();
                    }
                }
            }

            String type = headStr.substring(0,headStr.indexOf(" "));

            String [] tmpHost = host.split(":");
            host = tmpHost[0];
            int port = 80;

            if (tmpHost.length>1){
                port = Integer.parseInt(tmpHost[1]);
            }

            // 连接目标服务器
            proxySocket = new Socket(host, port);
            proxyIn = proxySocket.getInputStream();
            proxyOt = proxySocket.getOutputStream();

            // 如果第一次连接直接返回HTTP/1.1 200 Connection Established，否则转发数据
            if ("CONNECT".equalsIgnoreCase(type)){
                clientOt.write("HTTP/1.1 200 Connection Established\r\n\r\n".getBytes());
                clientOt.flush();
            }else {
                // 请求转发
                proxyOt.write(headStr.toString().getBytes());
                proxyOt.flush();
            }

            //新开线程转发客户端请求至目标服务器
            new ProxyHandleThread(clientIn, proxyOt).start();
            //转发目标服务器响应至客户端


//            String forwardLine;
//            BufferedReader forwardBuffer = new BufferedReader(new InputStreamReader(proxyIn));
//
//            while ((forwardLine=forwardBuffer.readLine())!=null) {
////                String proxyResponse = IOUtils.toString(proxyIn);
//                System.out.println(">>>"+forwardLine);
//                clientOt.write(forwardLine.getBytes());
//            }

            while (true){
                clientOt.write(proxyIn.read());
            }
        }catch (Exception ex){
            //pass
        }finally {
            if (proxyIn != null) {
                try {
                    proxyIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (proxyOt != null) {
                try {
                    proxyOt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (proxySocket != null) {
                try {
                    proxySocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientIn != null) {
                try {
                    clientIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (clientOt != null) {
                try {
                    clientOt.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
