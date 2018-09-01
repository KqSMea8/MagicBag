package org.github.helixcs.java;

public class LoopPrintWithThread {

    private static final Object lock = new Object();
    private static String tag = "A";

    private static void planA(){
        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (lock){
                        if(!tag.equalsIgnoreCase("A")){
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }else {
                            System.out.println("A");
                            tag = "B";
//                            lock.notifyAll();
                        }
                    }
                }
            }
        });

        Thread bThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (lock){
                        if(!tag.equalsIgnoreCase("B")){
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }else {
                            System.out.println("B");
                            tag = "C";
//                            lock.notifyAll();

                        }
                    }
                }
            }
        });

        Thread cThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    synchronized (lock){
                        if(!tag.equalsIgnoreCase("C")){
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }else {
                            System.out.println("C");
                            tag = "A";
//                            lock.notifyAll();
                        }
                    }
                }
            }
        });

        aThread.start();
        bThread.start();
        cThread.start();
    }

    public static void main(String[] args) throws Exception{
        planA();

    }

}
