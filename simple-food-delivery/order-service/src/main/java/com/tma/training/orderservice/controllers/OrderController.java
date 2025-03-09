package com.tma.training.orderservice.controllers;

import com.tma.training.orderservice.models.OrderRequest;
import com.tma.training.orderservice.models.OrderResponse;
import com.tma.training.orderservice.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Started creating order {}, {}", orderRequest, Thread.currentThread().isVirtual());
        OrderResponse response = orderService.createOrder(orderRequest);
        log.info("Finished creating order {}", response.orderId());
        return response;
    }

}