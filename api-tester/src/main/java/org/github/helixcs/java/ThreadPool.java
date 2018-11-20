package org.github.helixcs.java;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPool {

    final class RetryThread extends Thread {
        private Thread thread;
        private int maxRetryCount;


    }

    static final class ScheduleWithMaxRetry {
        private static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        private Thread thread;
        private int maxRetiesCount;
        private int delay = 5;
        private int period;
        private TimeUnit timeUnit;

        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public int getMaxRetiesCount() {
            return maxRetiesCount;
        }

        public void setMaxRetiesCount(int maxRetiesCount) {
            this.maxRetiesCount = maxRetiesCount;
        }

        public int getDelay() {
            return delay;
        }

        public void setDelay(int delay) {
            this.delay = delay;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit) {
            this.timeUnit = timeUnit;
        }

        private void execute() {

        }

    }

    // ======>


    private static ThreadLocal<AtomicInteger> maxRetryCount = new ThreadLocal<AtomicInteger>();


    private static final ScheduledExecutorService scheduleService = Executors.newScheduledThreadPool(3);


    private void uploadTask(String parameter) {
        System.out.println(maxRetryCount.get()+" start to upload by " + parameter);
    }

    private void tryNewScheduleThreadPoolWithMax(Thread workThread, int maxCount, int period, TimeUnit timeUnit) {
        scheduleService.scheduleAtFixedRate(new Thread(() -> uploadTask("线程1")), 5, period, timeUnit);
    }

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
//        threadPool.tryNewScheduleThreadPool();
        threadPool.tryNewScheduleThreadPoolWithMax(new Thread(() -> threadPool.uploadTask("线程2")), 5, 5, TimeUnit.SECONDS);
        threadPool.tryNewScheduleThreadPoolWithMax(new Thread(() -> threadPool.uploadTask("线程1")), 5, 5, TimeUnit.SECONDS);

    }
}
