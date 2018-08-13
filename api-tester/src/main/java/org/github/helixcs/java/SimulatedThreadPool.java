package org.github.helixcs.java;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Helixcs
 * @Time:7/18/18
 *
 * 简易线程池实现
 */
public class SimulatedThreadPool {

    private static Integer defaultWorkNum =5;

    // 任务列表
    private final static  List<Runnable> workQueue = new LinkedList<>();

    public SimulatedThreadPool(){
        this(defaultWorkNum);
    }

    public  SimulatedThreadPool(int workNum) {
        defaultWorkNum = workNum;
        for (int i=0;i<workNum;i++){
            Thread threadPool = new WorkThread();
            threadPool.start();
        }
    }

    public static SimulatedThreadPool getThreadPool(int workNum){
        if (workNum<=0){
            return new SimulatedThreadPool();
        }else {
            return new SimulatedThreadPool(workNum);
        }
    }

    public static SimulatedThreadPool getThreadPool(){
        return getThreadPool(defaultWorkNum);
    }


    public void  execute(Runnable []runnables){
        workQueue.addAll(Arrays.asList(runnables));

    }

    public  void execute(List<Runnable> runnableList){
        workQueue.addAll(runnableList);
    }

    public  void execute(Runnable runnable){
        workQueue.add(runnable);
    }

    // 生成单独的线程，消费任务列表中的任务
    private class WorkThread extends  Thread{
        private boolean isRunning = true;

        Runnable r = null;
        @Override
        public void run() {
            while (isRunning) {
                synchronized (workQueue){
                    if(!workQueue.isEmpty()){
                       r=  workQueue.remove(0);
                    }
                }
                if(r!=null){
                    r.run();
                }
                r=null;
            }

        }


    }

    static  class Task implements Runnable{

        @Override
        public void run() {
            System.out.println("==> "+Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        SimulatedThreadPool simulatedThreadPool = SimulatedThreadPool.getThreadPool();
        simulatedThreadPool.execute(Arrays.asList(new Task(),new Task(),new Task()));


    }

}
