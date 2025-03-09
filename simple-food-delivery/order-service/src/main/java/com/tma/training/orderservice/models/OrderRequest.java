package com.tma.training.orderservice.models;

public record OrderRequest(
        String customerName,
        String foodItem,
        String restaurantName
) {
}
