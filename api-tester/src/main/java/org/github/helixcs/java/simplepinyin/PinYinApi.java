package org.github.helixcs.java.simplepinyin;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;

public class PinYinApi {



    public static List<String> spendString(List<String[]> addString, List<String> sourceString) {
        if (addString.size() == 0 || addString.get(0).length == 0) {
            return sourceString;
        }
        List<String> result = null;
        if (sourceString.size() == 0) {
            result = new ArrayList<>(addString.get(0).length);
            for (String temp : addString.get(0)) {
                result.add(temp);
            }
        } else {
            result = new ArrayList<>(sourceString.size() * addString.get(0).length);
            for (String oldString : sourceString) {
                for (String addTemp : addString.get(0)) {
                    result.add(oldString + addTemp);
                }
            }
        }
        return spendString(addString.subList(1, addString.size()), result);
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {

        String[] duoyinList = "於,单,折,喝,着,蕃,量,沓,烊,载,曝,宁,和,省,拗,臭,度,哄,丧,差,扎,埋,盛,伧,创,伯,疟,看,行,艾,把,传,荷,涨,奇,炮,给,冠,干,巷,薄,拓,恶,便,宿,号,藏,轧,卡,调,模,没,舍,殷,还,系,假,降,脯,间,石,劲,茄,刨,弹,颤,扒,散,数,参,会,簸,吓,胖,耙,伺,好,咳,处,囤,缝,澄,扇,得,屏,几,卷,乐,了,吭,粘,畜,称,弄,俩,露,重,率,空,泊,朝,膀,校,强,塞,辟,倒,都,匙".split(",");
        List<String> failedList = new ArrayList<>();
        Set<String> successList = new HashSet<>();
        for (String s : duoyinList) {
            Set<String> py = new HashSet<>(Arrays.asList(Pinyin.toPinyin(s.charAt(0))));
            if (py.size() < 2) {
                failedList.add(s + "-->" + (py));
            }
            successList.add(s + "-->" + py);
        }


        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);


        List<String> failed4JList = new ArrayList<>();
        Set<String> success4JList = new HashSet<>();

        for (String s : duoyinList) {
            Set<String> py = new HashSet<>(Arrays.asList(PinyinHelper.toHanyuPinyinStringArray(s.charAt(0), format)));
            if (py.size() < 2) {
                failed4JList.add(s + "-->" + py);
            }
            success4JList.add(s + "-->" + py);

        }

        System.out.println(failedList.size());
        System.out.println(failedList);
        System.out.println(failed4JList.size());
        System.out.println(failed4JList);

        System.out.println("========");
        System.out.println(successList.size());
        System.out.println(successList);
        System.out.println(success4JList.size());
        System.out.println(success4JList);

        String s = "於佳佳";
        System.out.println(Pinyin.getDuoYinString(s));
    }
}
