/*
    Resolve the issue from AppA04
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AppB04 {

    private static final Logger log = LoggerFactory.getLogger(AppB04.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) {
        try {
            OrderDto orderDto = getResponse();
            log.info("orderDto {}", orderDto);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Sub task exception caught", e);
        }
    }

    private static OrderDto getResponse() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userFuture = scope.fork(AppB04::findUser);
            var orderFuture = scope.fork(AppB04::fetchOrder);
            scope.join(); // wait for tasks to complete
            scope.throwIfFailed();
            return new OrderDto(userFuture.get(), orderFuture.get());
        }
    }

    private static String findUser() throws InterruptedException {
        log.info("findUser() running!");
        Thread.sleep(5000); // Simulating long execution
        log.info("findUser() completed!");
        return "user";
    }

    private static int fetchOrder() throws InterruptedException {
        log.info("fetchOrder() running!");
        Thread.sleep(500);
        throw new RuntimeException("Order retrieval failed immediately!"); // Simulate fast failure
    }

}
