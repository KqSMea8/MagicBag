/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.java;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalApi {
    // 人民币分转元
    private static BigDecimal fenToYuan(BigDecimal fenDecimal){
        return fenDecimal.divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
    }

    /**
     * BigDecimal 类型按照精度转Long 类型
     * @param yuanBigDecimal        BigDecimal 类型金额
     * @param  accuracy             精度
     * @return  Long
     */
    public static Long bigDecimalToLongWithAccuracy(BigDecimal yuanBigDecimal,int accuracy){
        return yuanBigDecimal.multiply(new BigDecimal(accuracy)).longValue();
    }

    /**
     * 元保留两位精度的金额转为 Long 类型分金额，example：20.6 -> 2060 ,注意只支持分精度！！
     * @param yuanBigDecimal       BigDecimal 类型金额
     * @return Long
     */
    public static  Long yuanToFen(BigDecimal yuanBigDecimal){
        return bigDecimalToLongWithAccuracy(yuanBigDecimal,100);
    }


    public static void main(String[] args) {
//        Long fenString = 2000L;
//        System.out.println(fenToYuan(BigDecimal.valueOf(fenString)).toString());
//
//        BigDecimal yuanBigDecimal = new BigDecimal(20.6);
//        System.out.println(yuanToFen(yuanBigDecimal));
        try{
            int i = 1/0;
        }catch (Exception ex){
            System.out.println(ex.getMessage()+"-"+ex.getStackTrace()[0]);
        }

    }
}
