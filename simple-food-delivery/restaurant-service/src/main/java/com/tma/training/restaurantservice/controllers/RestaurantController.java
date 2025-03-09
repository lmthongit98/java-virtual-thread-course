package com.tma.training.restaurantservice.controllers;

import com.tma.training.restaurantservice.models.RestaurantOrderRequest;
import com.tma.training.restaurantservice.models.RestaurantOrderResponse;
import com.tma.training.restaurantservice.services.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/assign")
    public RestaurantOrderResponse confirmOrder(@RequestBody RestaurantOrderRequest request) {
        log.info("Started preparing order {}, {}", request.orderId(), Thread.currentThread().isVirtual());
        return restaurantService.prepareOrder(request);
    }

}