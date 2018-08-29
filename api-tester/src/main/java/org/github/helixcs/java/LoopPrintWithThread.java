package org.github.helixcs.java;

import java.util.Arrays;

public class LoopPrintWithThread {

    private static String [] tags = new String[]{"A","B","C"};

    static class ABCPrintThread extends  Thread{
        private Integer tagIndex;
        private volatile  int state ;
        private final Object lock = new Object();

        ABCPrintThread(Integer tag){
            this.tagIndex = tag;
        }


        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (lock){
                    while (state%3!=0){
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(tags[tagIndex]);
                    state++;
                    lock.notifyAll();
                }
            }

        }
    }

    private static class MyThread extends Thread {
        int which; // 0：打印A；1：打印B；2：打印C
        static volatile int state; // 线程共有，判断所有的打印状态
        static final Object t = new Object();
        public MyThread(int which) {
            this.which = which;
        }
        @Override
        public void run() {
            for (;;) {
                synchronized (t) {
                    while (state % 3 != which) {
                        try {
                            t.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(tags[which]); // 执行到这里，表明满足条件，打印
                    state++;
                    t.notifyAll(); // 调用notifyAll方法
                }
            }
        }
    }

    public static void main(String[] args) throws Exception{
        new MyThread(0).start();
        new MyThread(1).start();
        new MyThread(2).start();
    }

}
