/*
      Resolve the issue from AppA03
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class AppB03 {

    private static final Logger log = LoggerFactory.getLogger(AppB03.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) throws InterruptedException {

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                try {
                    OrderDto orderDto = getResponse();
                    log.info("orderDto {}", orderDto);
                } catch (Exception e) {
                    log.error("Getting exception!", e);
                }
            });
            Thread.sleep(3000); // prevent app from exiting for 3s
        }
    }

    private static OrderDto getResponse() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userFuture = scope.fork(AppB03::findUser);
            var orderFuture = scope.fork(AppB03::fetchOrder);
            Thread.sleep(500);
            log.info("is parent thread interrupted {}", Thread.currentThread().isInterrupted());
            Thread.currentThread().interrupt();
            log.info("is parent thread interrupted {}", Thread.currentThread().isInterrupted());
            scope.join(); // will throw exception if current thread is interrupted
            scope.throwIfFailed();
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

}
