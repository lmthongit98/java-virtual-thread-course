package com.tma.training.assignments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ProductService {
    private static final Map<Integer, String> PRODUCT_DB = new HashMap<>();

    static {
        IntStream.rangeClosed(1, 10000).forEach(i -> PRODUCT_DB.put(i, STR."Product-\{i}"));
    }


    // Simulated network latency
    public static String fetchProductName(int productId) {
        try {
            System.out.println("Started fetching product name with id: " + productId);
            Thread.sleep(1000); // Simulate 1 second network delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return PRODUCT_DB.getOrDefault(productId, "Unknown");
    }

    public static List<Integer> getSampleProductIds() {
        return new ArrayList<>(PRODUCT_DB.keySet());
    }
}
