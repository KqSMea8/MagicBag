package org.github.helixcs.java.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: helix
 * @Time:9/16/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class FileChannelSample {

    private static final  String  resourcePath = "api-tester/src/main/resources/";
    private static void readFile() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile(resourcePath+"nio_sample.txt","rw");
        FileChannel fileChannel = accessFile.getChannel();

        // 创建 Buffer
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int bytesRead = fileChannel.read(buffer);
        while (bytesRead!=-1){
            // buffer 开始读
            buffer.flip();
            while (buffer.hasRemaining()){
                // 每次读1byte
                System.out.println((char)buffer.get());
            }
            buffer.clear();
            bytesRead = fileChannel.read(buffer);
        }

        accessFile.close();
    }

    private static void writeFile() throws  Exception {
        File file = new File(resourcePath+"data.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel fileChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("this a NIO write file test".getBytes());
        buffer.flip();
        fileChannel.write(buffer);
        fileChannel.close();
        fileOutputStream.close();
    }

    public static void main(String[] args) throws Exception {
//        readFile();
        writeFile();
    }
}
