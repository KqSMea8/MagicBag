package org.github.helixcs.java.nio;

import io.netty.util.internal.PlatformDependent;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ReportDirectMemory {
    private  AtomicLong directMemory;

    @PostConstruct
    public void init() throws IllegalAccessException {
        Field field = ReflectionUtils.findField(PlatformDependent.class, "DIRECT_MEMORY_COUNTER");
        assert field != null;
        field.setAccessible(true);
        directMemory = (AtomicLong) field.get(PlatformDependent.class);
    }

    private void startReport() {
        Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                return null;
            }
        }).scheduleAtFixedRate(this::doReport, 0, 1, TimeUnit.SECONDS);

//        ExecutorService schedulePool = Executors.newScheduledThreadPool(10);
//        schedulePool.submit(this::doReport);

    }

    private void doReport() {
        int memoryInKb = (int) (directMemory.get() / 1024);
        System.out.println("DirectMemory : " + memoryInKb);

    }

    public static void main(String[] args) {
        ReportDirectMemory reportDirectMemory = new ReportDirectMemory();
        while (true) {
            reportDirectMemory.startReport();
        }
    }
}
