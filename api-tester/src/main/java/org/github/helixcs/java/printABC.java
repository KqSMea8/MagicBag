/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package org.github.helixcs.java;

/**
 * @Author: Helixcs
 * @Time:8/29/18
 */
public class printABC {
    private static int state = 0;
    public static void main(String[] args) {
        final printABC t = new printABC();
        Thread A = new Thread(new Runnable() {
            public void run() {
                // 设定打印10次
                for (int i = 0; i < 10; i++) {
                    synchronized (t) {
                        // 如果不满足A的打印条件，则调用wait，一直阻塞
                        while (state % 3 != 0) {
                            try {
                                t.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        // 执行到这里，表明满足条件，打印A，设置state
                        // 调用notifyAll方法
                        System.out.print("A");
                        state++;
                        t.notifyAll();
                    }
                }
            }
        });
        Thread B = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (t) {
                        while (state % 3 != 1) {
                            try {
                                t.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("B");
                        state++;
                        t.notifyAll();
                    }
                }
            }
        });
        Thread C = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (t) {
                        while (state % 3 != 2) {
                            try {
                                t.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("C");
                        state++;
                        t.notifyAll();
                    }
                }
            }
        });
        A.start();
        B.start();
        C.start();
    }
}
