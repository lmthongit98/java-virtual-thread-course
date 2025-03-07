/*
 * RACE CONDITIONS
 */

package com.tma.training.demo07_race_condition;

import java.util.stream.IntStream;



public class AppA {

    public static void main(String[] args) {
        final int NUM_THREADS = 4;


        var lstTh = IntStream.range(0, NUM_THREADS)
                .mapToObj(i -> new Thread(() -> {
                    try { Thread.sleep(1000); } catch (InterruptedException e) { }
                    System.out.print(i);
                }))
                .toList();


        lstTh.forEach(Thread::start);
    }

}
