package com.tma.training.restaurantservice.controllers;

import com.tma.training.restaurantservice.models.RestaurantOrderRequest;
import com.tma.training.restaurantservice.models.RestaurantOrderResponse;
import com.tma.training.restaurantservice.services.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Random;

import static com.tma.training.restaurantservice.services.RestaurantService.PRODUCT_DB;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);

    private final RestaurantService restaurantService;
    private final Random random = new Random();

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<String> getProductById(@PathVariable int id) throws InterruptedException {
        Thread.sleep(1000); // simulate delay
        if (!PRODUCT_DB.containsKey(id)) {
            return ResponseEntity.status(404).body("Product not found");
        }

        return ResponseEntity.ok(PRODUCT_DB.get(id));
    }


    @GetMapping("/product/{id}/rating")
    public ResponseEntity<Integer> getProductRating(@PathVariable int id) throws InterruptedException {
        Thread.sleep(1000); // simulate delay
        if (!PRODUCT_DB.containsKey(id)) {
            return ResponseEntity.status(404).body(null);
        }
        int rating = random.nextInt(5) + 1;

        return ResponseEntity.ok(rating);
    }

    @PostMapping("/assign")
    public RestaurantOrderResponse confirmOrder(@RequestBody RestaurantOrderRequest request) {
        log.info("Started preparing order {}, {}", request.orderId(), Thread.currentThread().isVirtual());
        return restaurantService.prepareOrder(request);
    }


}