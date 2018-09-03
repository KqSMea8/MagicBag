package org.github.helixcs.algorithm;

import java.util.*;

public class LetCode {

//    给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。
//
//    你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
//
//    示例:
//
//    给定 nums = [2, 7, 11, 15], target = 9
//
//    因为 nums[0] + nums[1] = 2 + 7 = 9

    public static List firstBlood(List<Integer> integerList ,Integer total){
        List<Set> finalResult = new ArrayList<>();
        Set<Integer> singleItem = new TreeSet<>();
        for(int i=0;i<integerList.size();i++){
            for (int j=i+1;j<integerList.size();j++){
                if(integerList.get(i)+integerList.get(j)==total){
                    singleItem.add(integerList.get(i));
                    singleItem.add(integerList.get(j));
                    finalResult.add(singleItem);
                }
            }
        }

        return finalResult;
    }

    public static  int[] twoSum(int[] nums, int target) {
        int [] r = new int[2];
        for(int i=0;i<nums.length;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==target){
                    r[0]  = i;
                    r[1] = j;
                    break;
                }
            }
        }
        return r;
    }

    public List transform(List<Integer> list ,Integer target){
        List<List> lists = new ArrayList<>();
        List<Integer> tmpList = new ArrayList<>();
        // 过滤大于等于target的值
        for(int i=0;i<list.size();i++){
            if(list.get(i)<target){
                tmpList.add(list.get(i));
            }
            if(list.get(i).equals(target)){
                lists.add(Collections.singletonList(lists.get(i)));
            }
        }
        int  tmpTotalValue = 0;
        for(int i = 0;i<tmpList.size();i++){
            if(tmpTotalValue>target){continue;}
            else if(tmpTotalValue<target){

            }
            tmpTotalValue += tmpList.get(i);

        }

        return null;
    }

    public static void main(String[] args) {
        List<Integer> a = Arrays.asList(2,7,11,15);
        System.out.println(firstBlood(a,9));
        int [] b = new int[]{2,7,11,15};
        System.out.println(Arrays.toString(twoSum(b, 9)));

    }
}
