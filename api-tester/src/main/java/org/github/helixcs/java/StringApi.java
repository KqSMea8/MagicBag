package org.github.helixcs.java;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class StringApi {
    private  static void  stringReMatch(){
        String string= "(MRT)84116748";
        String substring = StringUtils.substring(string,string.indexOf(")")+1,string.length());
        String finalString = Pattern.compile("[^0-9]").matcher(substring).replaceAll("").trim();
        System.out.println(finalString);
    }
    public static void main(String[] args) {
        stringReMatch();
    }
}
