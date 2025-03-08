/*
   Resolve the issue from AppA02
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class AppB02 {

    private static final Logger log = LoggerFactory.getLogger(AppB02.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) {
        try {
            OrderDto orderDto = getResponse();
            log.info("orderDto {}", orderDto);
        } catch (RuntimeException | ExecutionException | InterruptedException e) {
            log.error("Sub task exception caught!", e);
        }
    }

    private static OrderDto getResponse() throws ExecutionException, InterruptedException {
        //  ShutdownOnFailure: If one task fails, this policy automatically cancels all other tasks.
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userFuture = scope.fork(AppB02::findUser);
            var orderFuture = scope.fork(AppB02::fetchOrder);
            scope.join(); // / Wait until ALL are complete or at least one fails
            scope.throwIfFailed();
//            scope.throwIfFailed(ex -> {
//                log.info("subtasks failed", ex);
//                return new RuntimeException("Something went wrong!");
//            });
            return new OrderDto(userFuture.get(), orderFuture.get());
        }
    }

    private static String findUser() throws InterruptedException {
        log.info("findUser() running!");
        Thread.sleep(500);
        throw new RuntimeException("User not found"); // Simulate failure
    }

    private static int fetchOrder() throws InterruptedException {
        log.info("fetchOrder() running!");
        Thread.sleep(2000);
        log.info("fetchOrder() completed!");
        return 100;
    }

}
