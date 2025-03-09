package com.tma.training.orderservice.clients;

import com.tma.training.orderservice.models.Order;
import com.tma.training.orderservice.models.RiderAssignmentResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

public class RiderServiceClient {

    private final RestClient client;

    public RiderServiceClient(RestClient client) {
        this.client = client;
    }

    public RiderAssignmentResponse assignRider(Order order) {
        return this.client.post()
                .uri("/assign")
                .body(order)
                .retrieve()
                .body(new ParameterizedTypeReference<RiderAssignmentResponse>() {
                });
    }

}
