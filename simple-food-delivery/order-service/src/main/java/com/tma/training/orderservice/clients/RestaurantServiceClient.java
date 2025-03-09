package com.tma.training.orderservice.clients;

import com.tma.training.orderservice.models.Order;
import com.tma.training.orderservice.models.RestaurantOrderResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class RestaurantServiceClient {

    private final RestClient client;

    public RestaurantServiceClient(RestClient client) {
        this.client = client;
    }

    public RestaurantOrderResponse assignRestaurant(Order order) {
        return this.client.post()
                .uri("/assign")
                .body(order)
                .retrieve()
                .body(new ParameterizedTypeReference<RestaurantOrderResponse>() {
                });
    }

}
