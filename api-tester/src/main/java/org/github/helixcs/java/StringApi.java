package org.github.helixcs.java;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class StringApi {

    /**
     * 字符串填充
     * @param targetString      目标字符串
     * @param paddingString     填充字符串
     * @param exceptedLength    期望长度
     * @param start             true-在开始位置填充 , false-在结尾位置填充
     * @return
     */
    private static String paddingString(String targetString, String paddingString, Integer exceptedLength,boolean start){
        if (null==targetString){
            return null;
        }
        int targetStringLength =targetString.length();
        if (targetStringLength>=exceptedLength){
            return targetString.substring(0,exceptedLength);
        }
        StringBuilder combinedPaddingSb = new StringBuilder();
        for(int i =0;i<(exceptedLength-targetStringLength);i++){
            combinedPaddingSb.append(paddingString);
        }
        if(start){
            targetString = combinedPaddingSb.toString()+targetString;
        }else {
            targetString = targetString+combinedPaddingSb.toString();
        }
        return targetString;
    }

    private  static void  stringReMatch(){
        String string= "(MRT)84116748";
        String substring = StringUtils.substring(string,string.indexOf(")")+1,string.length());
        String finalString = Pattern.compile("[^0-9]").matcher(substring).replaceAll("").trim();
        System.out.println(finalString);
    }

    private static void stringSplit(){
        String roomNum = "2508";
        String singleRoomNum = roomNum.substring(roomNum.length()-2,roomNum.length());
        System.out.println(singleRoomNum);
    }
    public static void main(String[] args) {
//        stringReMatch();
//        stringSplit();
        System.out.println(paddingString("zha","+",10,false));
    }
}
