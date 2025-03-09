package com.tma.training.orderservice.models;

public record OrderResponse(
        String orderId,
        String message
) {
}
