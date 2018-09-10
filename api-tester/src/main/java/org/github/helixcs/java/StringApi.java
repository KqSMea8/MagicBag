package org.github.helixcs.java;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
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

    private static final String psbConfigString = "[{\"key\":\"url\",\"value\":\"http://www.haikouhotel.net:29090/post\",\"explain\":\"大河服务器地址\"},{\"key\":\"hotelCode\",\"value\":\"\",\"explain\":\"旅馆代码\"},{\"key\":\"key\",\"value\":\"\",\"explain\":\"密钥\"}]";

    public static  String getConfig(String conf){

        conf = StringUtils.replace(conf,"[{\"key\":", "{");
        conf = StringUtils.replaceAll(conf,",\"explain\":\".{0,20}\"},\\{\"key\":", ",");
        conf = StringUtils.replaceAll(conf,",\"explain\":\".{0,20}\"}]", "}");
        conf = StringUtils.replace(conf,"},{\"key\":", ",");
        conf = StringUtils.replace(conf,",\"value\"", "");
        conf = StringUtils.replace(conf,"]", "");

        return conf;
    }
    private static String getConfig(String config, String json){
        List<JSONObject> jsonArray = JSONArray.parseArray(psbConfigString,JSONObject.class);
        JSONObject resultJson = new JSONObject();
        for(JSONObject jsonObject:jsonArray){
            if(jsonObject.getString("key").equalsIgnoreCase("url")){
                resultJson.put("url",jsonObject.getString("value"));
            }else if(jsonObject.getString("key").equalsIgnoreCase("hotelCode")){
                resultJson.put("hotelCode", jsonObject.getString("value"));
            }else if(jsonObject.getString("key").equalsIgnoreCase("key")){
                resultJson.put("key",jsonObject.getString("value"));
            }
        }
        return resultJson.toJSONString();
    }
    public static void main(String[] args) {

        System.out.println(getConfig(psbConfigString,""));
        System.out.println(getConfig(psbConfigString));
//        stringReMatch();
//        stringSplit();
//        System.out.println(paddingString("zha","+",10,false));
//        System.out.println("sdsas".concat("fsad"));
    }
}
