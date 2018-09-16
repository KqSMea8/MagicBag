package org.github.helixcs.java.nio;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @Author: helix
 * @Time:9/17/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class SocketUtils {

    // 开始符
    public static final byte STX = 0x02;
    // 终止符
    public static final byte ETX = 0x03;

    //CR
    public static final byte CR = '\r';

    //LF
    public static final  byte LF = '\n';

    public static final byte[] CRLF = {CR,LF};

    /**
     * 判断socket开始结束帧的参数转化
     * @param arguments         字符串参数数组
     * @return                  byte 数组
     * @throws IOException
     */
    public static  byte [] stringArrayToByteWithStartAndEnd(String ... arguments) throws IOException {
        return stringArrayToByte(true, arguments);
    }

    /**
     * 不判断socket开始结束帧的参数转化
     * @param arguments             字符串参数数组
     * @return                      byte 数组
     * @throws IOException
     */
    public static byte [] stringArrayToByteWithoutStartAndEnd(String ... arguments) throws IOException {
        return stringArrayToByte(false, arguments);
    }

    /**
     * 字符串转 byte 数组
     * @param startAndEndFrame          是否判断socket开始结束帧
     * @param arguments                 字符串参数数组
     * @return
     * @throws IOException
     */
    public static byte [] stringArrayToByte(boolean startAndEndFrame,String ... arguments) throws IOException {
        StringByteArrayOutputStream stringByteArrayOutputStream = new StringByteArrayOutputStream(Charset.defaultCharset());
        if(startAndEndFrame){
            stringByteArrayOutputStream.write(STX);
        }
        for(String argument:arguments){
            stringByteArrayOutputStream.write(argument);
        }
        if(startAndEndFrame){
            stringByteArrayOutputStream.write(ETX);
        }
        byte [] bytes = stringByteArrayOutputStream.toByteArray();
        stringByteArrayOutputStream.close();
        return bytes;
    }

    /**
     * 截取可用的字符（STX - ETX 中间数据部分 byte 数组）
     * @param data          byte 数组
     * @return              byte 数组
     */
    public static byte[] fetchVaildByte(byte[] data) {
        if (null == data || data.length == 0) {
            return null;
        }
        int start = -1, end = -1;
        // 起始值STX 0x02索引 终止标识
        // 结束值ETX 0x03索引 取最后0x03终止, 无需终止标识
        boolean startTerminationFlag = false,endTerminationFlag = false;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == STX && !startTerminationFlag) {
                start = i;
                startTerminationFlag =  true;
            }
            if (data[i] == ETX && !endTerminationFlag) {
                end = i;
                endTerminationFlag = true;
            }
            if(start!=-1 && end!=-1){
                break;
            }
        }
        if (start == -1 || end == -1) {
            return null;
        }
        return Arrays.copyOfRange(data,start+1, end);
    }

    /**
     * 截取可用的字符（STX - ETX 中间数据部分字符串）
     * @param data          byte 数组
     * @return              byte 数组
     */
    public static String fetchVaildString(byte [] data){
        return new String(fetchVaildByte(data));
    }
}
