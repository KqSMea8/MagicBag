package org.github.helixcs.java.desgin;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductAndConsumer {


    public static void main(String[] args) {
        ProductAndConsumer productAndConsumer = new ProductAndConsumer();
//        PlanA planA = productAndConsumer.new PlanA();
//        planA.start();
//
//        PlanB planB = productAndConsumer.new PlanB();
//        planB.start();

        PlanC planC = productAndConsumer.new PlanC();
        planC.start();
    }

    // PlanA
    private static Integer count = 0;
    private static final Integer full = 10;
    private static final String lock = "lock";

    // PlanB
    private Lock rlock = new ReentrantLock();
    //创建两个条件变量，一个为缓冲区非满，一个为缓冲区非空
    private final Condition notFull = rlock.newCondition();
    private final Condition notEmpty = rlock.newCondition();

    // PlanC
    private final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10);

    private class PlanA {

        private void start() {
            PlanA planA = new PlanA();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
            new Thread(planA.new Producer()).start();
            new Thread(planA.new Consumer()).start();
        }

        class Producer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (lock) {
                        while (count == full) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        count++;
                        System.out.println(Thread.currentThread().getName() + "生产者,目前一共:" + count);
                        lock.notifyAll();
                    }
                }
            }
        }


        class Consumer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (lock) {
                        while (count == 0) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        count--;
                        System.out.println(Thread.currentThread().getName() + "消费者,目前一共:" + count);
                        lock.notifyAll();
                    }
                }
            }
        }

    }


    private class PlanB {
        private void start() {
            PlanB planB = new PlanB();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
            new Thread(planB.new Producer()).start();
            new Thread(planB.new Consumer()).start();
        }

        private class Producer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    // 获取锁
                    rlock.lock();
                    try {
                        while (count.equals(full)) {
                            notEmpty.await();
                        }

                        count++;
                        System.out.println("producer start , " + count);
                        notEmpty.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        rlock.unlock();
                    }

                }
            }
        }

        private class Consumer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    rlock.lock();
                    try {
                        while (count == 0) {

                            notFull.await();
                        }
                        count--;
                        System.out.println("consumer start, " + count);
                        notFull.signal();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        rlock.unlock();
                    }

                }
            }
        }
    }


    private class PlanC {

        private void start() {
            PlanC planC = new PlanC();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
            new Thread(planC.new Producer()).start();
            new Thread(planC.new Consumer()).start();
        }

        private class Producer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        blockingQueue.put(1);
                        count++;
                        System.out.println("producer starting , " + Thread.currentThread().getName() + "=" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private class Consumer implements Runnable {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        blockingQueue.take();
                        count--;
                        System.out.println("consumer starting, " + Thread.currentThread().getName() + "=" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
}

