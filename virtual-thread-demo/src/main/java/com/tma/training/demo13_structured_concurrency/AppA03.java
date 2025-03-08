/*
    Even if the parent thread running getResponse() is interrupted, the subtasks would continue to run.
    The interruption will not be propagated to the subtask threads.

*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppA03 {

    private static final Logger log = LoggerFactory.getLogger(AppA03.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) throws InterruptedException {

        try (var executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            executorService.submit(() -> {
                try {
                    OrderDto orderDto = getResponse(executorService);
                    log.info("orderDto {}", orderDto);
                } catch (RuntimeException | InterruptedException e) {
                    log.error("Getting exception!", e);
                }
            });
            Thread.sleep(3000); // prevent app from exiting for 3s
        }
    }

    private static OrderDto getResponse(ExecutorService executorService) throws InterruptedException {
        Future<String> user = executorService.submit(AppA03::findUser);
        Future<Integer> order = executorService.submit(AppA03::fetchOrder);

        // event parent thread fail, child threads are still running
        Thread.sleep(500);
        throw new RuntimeException("Something went wrong!");
//        String theUser = user.get();
//        int theOrder = order.get();
//        return new OrderDto(theUser, theOrder);
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
