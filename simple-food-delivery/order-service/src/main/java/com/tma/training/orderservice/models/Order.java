package com.tma.training.orderservice.models;

public record Order(
        String orderId,
        String customerName,
        String foodItem,
        String restaurantName,
        String status
) {
}
