package org.github.helixcs.java;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private void tryNewScheduleThreadPool() {
        ExecutorService singleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        singleThreadScheduledExecutor.submit(new Callable<Map>() {
            @Override
            public Map call() throws Exception {
                return null;
            }
        });


    }
}
