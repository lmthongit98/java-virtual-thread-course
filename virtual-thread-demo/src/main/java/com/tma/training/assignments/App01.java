/*
    VERSION 01: SINGLE THREAD
 */

package com.tma.training.assignments;

import java.util.ArrayList;
import java.util.List;

public class App01 {
    public static void main(String[] args) {
        List<Integer> ids = ProductService.getSampleProductIds();
        List<String> results = new ArrayList<>();

        long start = System.currentTimeMillis();

        for (int id : ids) {
            results.add(ProductService.fetchProductName(id));
        }

        long end = System.currentTimeMillis();

        System.out.println("Single Threaded Results: " + results);
        System.out.println("Time Taken: " + (end - start) + " ms");
    }
}
