/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.algorithm;

import java.util.*;

/**
 * @Author: Helixcs
 * @Time:9/1/18
 */
public class NAndM {
    // 支持Set 的 list
    static  class MyList<E> extends ArrayList<E> implements Comparable<E>{
        private E e;

        private MyList(){}

        private MyList(E... es){
            this.addAll(Arrays.asList(es));
        }

        // 0-替换父节点 1-插入右节点 -1 - 插入左节点
        @Override
        public int compareTo(E otherE) {

            if(null==otherE){return 1;}
            if(otherE==this||otherE.equals(this)){return 0;}
            if(otherE instanceof  List){
                List otherList = (List) otherE;
                List eList = this;

                if(otherList.size()!=eList.size()){return 1;}
                int size = eList.size();
                for(int i = 0;i<size;i++){
                    if(otherList.get(i).equals(eList.get(i))){
                        return 0;
                    }
                }
                return 1;
            }
            return 1;
        }
    }

    private static List<List> nm(List<Integer> integers , Integer target){
        List<List> finalList=  new ArrayList<>();
        List<Integer> tmpList = new ArrayList<>();
        for(int i=0;i<integers.size();i++){
            if(integers.get(i).equals(target)){
                finalList.add(Collections.singletonList(integers.get(i)));
            }
            if(integers.get(i)<target){
                tmpList.add(integers.get(i));
            }
        }

        for(int i=0;i<tmpList.size();i++){
            int firstValue = tmpList.get(i);
            // 记录池
            List<Integer> indexed = new ArrayList<>();
            for(int j =i+1;j<tmpList.size();j++){
                int secondValue = tmpList.get(j);

                int sumValue = firstValue+secondValue;
                if(sumValue==target){
                    indexed.add(firstValue);
                    indexed.add(secondValue);
                    finalList.add(indexed);
                }else if(sumValue>target){
                    break;
                }
            }
        }

        for(int i=0;i<tmpList.size();i++){
            List<Integer> indexList= new ArrayList<>();
            int reduceValue = tmpList.get(i);
            indexList.add(tmpList.get(i));
            first:
            for(int j=i+1;j<tmpList.size();j++){
                reduceValue = reduceValue+tmpList.get(j);
                indexList.add(tmpList.get(j));

                if(reduceValue>target){
                    break first;
                }else if(reduceValue==target){
                    finalList.add(indexList);
                    break first;
                }else {
                    // pass
                }

            }
        }

        return finalList;
    }

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1,2,3,4,5);
        Integer target  = 5;
        System.out.println(nm(integers,target));

        Set<Integer> set = new TreeSet<>();
        set.add(1);
    }
}
