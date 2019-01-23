package org.github.helixcs.algorithm.sort;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Arrays;

/**
 * @Author: helix
 * @Time:9/6/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class Sorted implements Serializable {
    private static  void bubbleSort(int p []){
        for(int i = 0;i<p.length-1;i++){
            for(int j = i+1;j<p.length-1;j++){
                if(p[j]>p[j+1]){
                    int tmpValue = p[j];
                    p[j] = p[j+1];
                    p[j+1] = tmpValue;
                }
            }

        }
    }
    // 选择排序 ，由低到高
    private static void  selectSort(int p[]){
        for(int i =0;i<p.length;i++){
            // 取任意一个数比较
            int min = i;
            System.out.println(MessageFormat.format("第{0} 次",i+1));
            for(int j = i+1;j<p.length;j++){
                if(p[min]>p[j]){
                    int tmpValue = p[min];
                    p[min]  =p[j];
                    p[j] = tmpValue;
                    min = j;
                }
                System.out.println(Arrays.toString(p));
            }
        }

    }
    // 插入排序
    private static void insertSort(int p[]){
        for(int i = 0;i<p.length;i++){
            System.out.println(MessageFormat.format("第{0} 次",i+1));
            for (int j = p.length-1;j>i;j--){
                if(p[i]>p[j]){
                    int tmpValue = p[i];
                    p[i] = p[j];
                    p[j] = tmpValue;
                }
                System.out.println(Arrays.toString(p));

            }
        }
    }
    public static void main(String[] args) {
        int a [] = new int[]{0,23,2,4,5,6,871,21};
//        System.out.println("选择排序:");
//        selectSort(a);
//        System.out.println(Arrays.toString(a));
//        System.out.println("插入排序:");
//        insertSort(a);
        bubbleSort(a);
        System.out.println(Arrays.toString(a));

    }
}
