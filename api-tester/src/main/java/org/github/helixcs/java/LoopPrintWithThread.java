package org.github.helixcs.java;

import java.util.concurrent.atomic.AtomicInteger;

public class LoopPrintWithThread {

    private static final Object lock = new Object();
    private static String tag = "A";

    private static void planA() {
        Thread aThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (lock) {
                        if (!tag.equalsIgnoreCase("A")) {
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        } else {
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
                while (true) {
                    synchronized (lock) {
                        if (!tag.equalsIgnoreCase("B")) {
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        } else {
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
                while (true) {
                    synchronized (lock) {
                        if (!tag.equalsIgnoreCase("C")) {
//                            try {
//                                lock.wait();
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        } else {
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

    private static void planB() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A");
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B");
            }
        });

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("C");
            }
        });

        threadA.start();
        threadA.join();

        threadB.start();
        threadB.join();

        threadC.start();
        threadC.join();
    }

    private static void planC() {
        AtomicInteger flag = new AtomicInteger(0);
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                if (flag.get() == 0) {
                    System.out.println("A");
                    flag.getAndIncrement();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                if (flag.get() == 1) {
                    System.out.println("B");
                    flag.getAndIncrement();
                }
            }
        });

        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                if (flag.get() == 2) {
                    System.out.println("C");
                    flag.set(0);
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();

    }

    public static void main(String[] args) throws Exception {
//        planA();
//        planB();
        for(;;){planC();}
    }


}
