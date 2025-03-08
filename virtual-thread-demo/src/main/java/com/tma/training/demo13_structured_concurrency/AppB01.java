/*
    Default Structured Concurrency (StructuredTaskScope<T>)
    If you use StructuredTaskScope directly, it does not automatically cancel other tasks when one fails or succeeds.
    Instead, it simply runs all tasks within the scope and waits for them to complete.
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AppB01 {

    private static final Logger log = LoggerFactory.getLogger(AppB01.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) {
        try {
            OrderDto orderDto = getResponse();
            log.info("orderDto {}", orderDto);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Sub task exception caught!", e);
        }
    }

    private static OrderDto getResponse() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope<>()) {
            // Each scope.fork creates a subtask that is bound to the scope. At this point, all tasks are created and running.
            var userFuture = scope.fork(AppB01::findUser);
            var orderFuture = scope.fork(AppB01::fetchOrder);
            var failingFuture = scope.fork(AppB01::failingTask);

            scope.join(); // wait for ALL to complete

            log.info("subtask1 state: {}", userFuture.state());
            log.info("subtask2 state: {}", orderFuture.state());
            log.info("subtask2 state: {}", failingFuture.state());

            return new OrderDto(userFuture.get(), orderFuture.get());
        }
    }

    private static String findUser() throws InterruptedException {
        log.info("findUser() running!");
        Thread.sleep(1000);
        log.info("findUser() completed!");
        return "user";
    }

    private static int fetchOrder() throws InterruptedException {
        log.info("fetchOrder() running!");
        Thread.sleep(2000);
        log.info("fetchOrder() completed!");
        return 100;
    }

    private static String failingTask() {
        throw new RuntimeException("oops");
    }
    
}
