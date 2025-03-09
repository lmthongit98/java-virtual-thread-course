package com.tma.training.restaurantservice.services;

import com.tma.training.restaurantservice.models.RestaurantOrderRequest;
import com.tma.training.restaurantservice.models.RestaurantOrderResponse;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {


    public RestaurantOrderResponse prepareOrder(RestaurantOrderRequest request) {
        try {
            String confirmationMessage = processOrder(request);
            return new RestaurantOrderResponse(request.orderId(), confirmationMessage);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return new RestaurantOrderResponse(request.orderId(), "Order prepare failed: " + e.getMessage());
        }
    }

    private String processOrder(RestaurantOrderRequest request) throws InterruptedException {
        Thread.sleep(1000); // Simulate processing delay
        return "Restaurant " + request.restaurantName() + " prepared order: " + request.orderId();
    }

}