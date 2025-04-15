package com.tma.training.demo10_task_types;

import com.tma.training.demo12_virtual_thread.demo01.VirtualThreadsWithBlockingCalls;
import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

/*
 *  What is the ideal pool size ?
 *  => It depends on the type of your tasks.
 */

public class App {

    private static final Logger log = LoggerFactory.getLogger(VirtualThreadsWithBlockingCalls.class);

    private static final int NUM_OF_TASKS = 1000;
    private static final int TASK_DURATION_MS = 100;
    private static final int NUMBER_OF_CPU_CORE = CommonUtils.getNumberOfCpuCores();

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        try (var executor = Executors.newFixedThreadPool(NUMBER_OF_CPU_CORE)) {
            for (int i = 0; i < NUM_OF_TASKS; i++) {
                executor.submit(App::performIOTask);
//                executor.submit(App::performCpuTask);
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("All tasks took: " + (end - start) + " ms");
    }


    private static void performIOTask() {
        log.info("Executing I/O bounded task ...");
        try {
            // similar network calls, file I/O, or database queries
            Thread.sleep(TASK_DURATION_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static long performCpuTask() {
        log.info("Executing CPU bounded task ...");
        long sum = 0;
        for (int i = 1; i <= 1000000; i++) {
            if (isPrime(i)) {
                sum += i;
            }
        }
        return sum;
    }

    public static boolean isPrime(int number) {
        if (number <= 1) return false;
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) return false;
        }
        return true;
    }


}
