/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.java;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Author: Helixcs
 * @Time:8/26/18
 */
public class ParamStreamApi {
    private static  final List<Integer> listForEach = new ArrayList<>();
    private static  final List<Integer> listParallelStream = new ArrayList<>();
    private static  final List<Integer> listParallelStreamWithLock = new ArrayList<>();
    private static  final  Lock lock =new ReentrantLock();
    private static void compareWithThreadSafe(){

        IntStream.range(0,10000).forEach(listForEach::add);
        IntStream.range(0,10000).parallel().forEach(listParallelStream::add);
        IntStream.range(0,10000).parallel().forEach(x->{
            lock.lock();
            try{
                listParallelStreamWithLock.add(x);
            }finally {
                lock.unlock();
            }
        });

        System.out.println("forEach List 中个数:"+listForEach.size());
        System.out.println("parallel List 中个数:"+listParallelStream.size());
        System.out.println("parallel List 加锁 个数:"+listParallelStreamWithLock.size());

    }

    public static void main(String[] args) {
        compareWithThreadSafe();
    }
}
