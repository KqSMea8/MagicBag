package org.github.helixcs.java;

import java.util.Arrays;

public class BytesApi {
    /**
     * 开始位
     */
    private final static byte STX = 0x02;
    /**
     * 结束位
     */
    private final static byte ETX = 0x03;

    private static String toReadableString(byte[] data) {
        if (null == data || data.length == 0) {
            return null;
        }
        int start = -1, end = -1;
        // 起始值STX 0x02索引 终止标识
        // 结束值ETX 0x03索引 取最后0x03终止, 无需终止标识
        boolean startTerminationFlag = false, endTerminationFlag = false;
        for (int i = 0; i < data.length; i++) {
            if (data[i] == STX && !startTerminationFlag) {
                start = i;
                startTerminationFlag = true;
            }
            if (data[i] == ETX && !endTerminationFlag) {
                end = i;
                endTerminationFlag = true;
            }
            if (start != -1 && end != -1) {
                break;
            }
        }
        if (start == -1 || end == -1) {
            return null;
        }
        System.out.println("> safLokUtils#toReadableString , data is :" + Arrays.toString(data) + ", start is " + start + ", end is :" + end);
        return new String(Arrays.copyOfRange(data, start + 1, end)).trim();
    }


    public static void main(String[] args) {
        byte[] bytes = new byte[]{2, 54, 50, 48, 48, 48, 48, 48, 48, 49, 48, 48, 48, 48, 49, 48, 126, 50, 52, 65, 70, 68, 61, 55, 52, 61, 54, 54, 54, 48, 57, 69, 68, 51, 61, 61, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        String result = toReadableString(bytes);
        System.out.println(result);

    }
}
