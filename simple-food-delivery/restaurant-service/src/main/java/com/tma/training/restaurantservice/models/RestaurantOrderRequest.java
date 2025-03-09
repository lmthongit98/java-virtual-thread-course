package com.tma.training.restaurantservice.models;

public record RestaurantOrderRequest(
        String orderId,
        String restaurantName
) {
}
