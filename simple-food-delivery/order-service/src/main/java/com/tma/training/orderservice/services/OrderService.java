package com.tma.training.orderservice.services;

import com.tma.training.orderservice.clients.RestaurantServiceClient;
import com.tma.training.orderservice.clients.RiderServiceClient;
import com.tma.training.orderservice.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final RestaurantServiceClient restaurantServiceClient;
    private final RiderServiceClient riderServiceClient;
    private final ExecutorService executor;

    public OrderService(RestaurantServiceClient restaurantServiceClient, RiderServiceClient riderServiceClient, ExecutorService executor) {
        this.restaurantServiceClient = restaurantServiceClient;
        this.riderServiceClient = riderServiceClient;
        this.executor = executor;
    }


    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = processCreatingOrder(orderRequest);

        Future<String> restaurantFuture = executor.submit(() -> assignRestaurant(order));
        Future<String> riderFuture = executor.submit(() -> assignRider(order));

        return new OrderResponse(order.orderId(), "Order placed successfully. " +
                getOrElse(restaurantFuture, null) + ". " + getOrElse(riderFuture, null));
    }

    private Order processCreatingOrder(OrderRequest orderRequest) {
        try {
            Thread.sleep(500); // Simulate delay
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String orderId = UUID.randomUUID().toString();
        return new Order(orderId, orderRequest.customerName(),
                orderRequest.foodItem(), orderRequest.restaurantName(), "PLACED");
    }

    private String assignRestaurant(Order order) {
        RestaurantOrderResponse response = restaurantServiceClient.assignRestaurant(order);
        return response.message();
    }

    private String assignRider(Order order) {
        RiderAssignmentResponse response = riderServiceClient.assignRider(order);
        return response.message();
    }

    private <T> T getOrElse(Future<T> future, T defaultValue) {
        try {
            return future.get();
        } catch (Exception e) {
            log.error("error", e);
        }
        return defaultValue;
    }

}