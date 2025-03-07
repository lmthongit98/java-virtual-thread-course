/*
 * Another technique similar to join is CountDownLatch
 */

package com.tma.training.demo02_join;


import com.tma.training.util.CommonUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class AppD {

    private static final int MAX_THREAD = 10;

    public static void main(String[] args) throws InterruptedException {

//        threadJoin();

        countDownLatch();

    }

    private static void countDownLatch() throws InterruptedException {
        var latch = new CountDownLatch(MAX_THREAD);

        var start = System.currentTimeMillis();
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = Thread.ofPlatform().unstarted(() -> {
                Task.doTask(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();

        var time = System.currentTimeMillis() - start;
        System.out.println("Tasks took: " + time + " ms");
    }

    private static void threadJoin() throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        var start = System.currentTimeMillis();
        for (int i = 0; i < MAX_THREAD; i++) {
            int j = i;
            Thread thread = Thread.ofPlatform().unstarted(() -> {
                Task.doTask(j);
            });
            list.add(thread);
            thread.start();
        }

        // wait for all threads to complete
        for (Thread thread : list) {
            thread.join();
        }

        var time = System.currentTimeMillis() - start;
        System.out.println("Tasks took: " + time + " ms");
    }


}


class Task {

    public static void doTask(int i){
        System.out.println("Starting task " + i);
        CommonUtils.sleep(Duration.ofSeconds(1));
        System.out.println("End task " + i);
    }

}

