package org.github.helixcs.java;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalApi {
    // 人民币分转元
    private static BigDecimal fenToYuan(BigDecimal fenDecimal){
        return fenDecimal.divide(new BigDecimal(100),2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        Long fenString = 2000L;
        System.out.println(fenToYuan(BigDecimal.valueOf(fenString)).toString());

    }
}
