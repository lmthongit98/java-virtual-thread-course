package com.tma.training.assignments;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductService {
    private static final Map<Integer, String> PRODUCT_DB = Map.of(
            1, "Apple", 2, "Banana", 3, "Carrot", 4, "Date", 5, "Eggplant",
            6, "Fig", 7, "Grapes", 8, "Honeydew", 9, "Iceberg Lettuce", 10, "Jackfruit"
    );

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
