package org.github.helixcs.java.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: helix
 * @Time:9/16/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class FileChannelSample {

    public static void readFile() throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("api-tester/src/main/resources/nio_sample.txt","rw");
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

    public static void main(String[] args) throws Exception {
        readFile();
    }
}
