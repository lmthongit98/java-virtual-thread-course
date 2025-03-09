package com.tma.training.restaurantservice.services;

import com.tma.training.restaurantservice.models.RestaurantOrderRequest;
import com.tma.training.restaurantservice.models.RestaurantOrderResponse;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RestaurantService {

    public static final Map<Integer, String> PRODUCT_DB = Map.ofEntries(
            Map.entry(1, "Pizza"), Map.entry(2, "Burger"), Map.entry(3, "Pasta"), Map.entry(4, "Sushi"), Map.entry(5, "Tacos"),
            Map.entry(6, "Steak"), Map.entry(7, "Ramen"), Map.entry(8, "Burrito"), Map.entry(9, "Dumplings"), Map.entry(10, "Salad"),
            Map.entry(11, "Sandwich"), Map.entry(12, "Fried Chicken"), Map.entry(13, "Lasagna"), Map.entry(14, "Pho"), Map.entry(15, "Kebab"),
            Map.entry(16, "Pad Thai"), Map.entry(17, "Fish & Chips"), Map.entry(18, "Shawarma"), Map.entry(19, "Mac & Cheese"), Map.entry(20, "Dim Sum"),
            Map.entry(21, "Hot Dog"), Map.entry(22, "Pancakes"), Map.entry(23, "Waffles"), Map.entry(24, "BBQ Ribs"), Map.entry(25, "Chow Mein"),
            Map.entry(26, "Gyro"), Map.entry(27, "Biryani"), Map.entry(28, "Gnocchi"), Map.entry(29, "Fajitas"), Map.entry(30, "Miso Soup"),
            Map.entry(31, "Poke Bowl"), Map.entry(32, "Clam Chowder"), Map.entry(33, "Curry"), Map.entry(34, "Lobster Roll"), Map.entry(35, "Gumbo"),
            Map.entry(36, "Paella"), Map.entry(37, "Quesadilla"), Map.entry(38, "Ceviche"), Map.entry(39, "Moussaka"), Map.entry(40, "Jambalaya"),
            Map.entry(41, "Risotto"), Map.entry(42, "Croissant"), Map.entry(43, "Cheesecake"), Map.entry(44, "Apple Pie"), Map.entry(45, "Tiramisu"),
            Map.entry(46, "Churros"), Map.entry(47, "Baklava"), Map.entry(48, "Gelato"), Map.entry(49, "Brownies"), Map.entry(50, "Cupcakes"),
            Map.entry(51, "Ice Cream")
    );


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