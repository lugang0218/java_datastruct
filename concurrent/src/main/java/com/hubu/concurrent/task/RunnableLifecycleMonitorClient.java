package com.hubu.concurrent.task;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
public class RunnableLifecycleMonitorClient {
    public static void main(String[] args) {
        conccurentQueryForIds(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), new RunnableLifecycleObserver());

        conccurentQueryForIds2(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9), new RunnableLifecycleObserver());
    }
    public static void conccurentQueryForIds(List<Integer> ids, RunnableLifecycleObserver listener) {
        ids.stream().forEach(id -> {
            new Thread(new ObservableRunnable(() -> {
                try {
                    // sleep some seconds to simulate doing work
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },listener)).start();
        });
    }

    public static void conccurentQueryForIds2(List<Integer> ids, RunnableLifecycleObserver listener) {
        ids.stream().forEach(id -> {
            new Thread(new ObservableRunnable(() -> {
                try {
                    // sleep some seconds to simulate doing work
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
                    if (id % 2 == 0) {
                        int i = 1 / 0;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },listener)).start();
        });
    }

}
