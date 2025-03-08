/*
    HAPPY CASE
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppA01 {

    private static final Logger log = LoggerFactory.getLogger(AppA01.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            OrderDto orderDto = getResponse(executorService);
            log.info("orderDto {}", orderDto);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Sub task exception caught!", e);
        }
    }

    private static OrderDto getResponse(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<String> user = executorService.submit(AppA01::findUser);
        Future<Integer> order = executorService.submit(AppA01::fetchOrder);
        String theUser = user.get();
        int theOrder = order.get();
        return new OrderDto(theUser, theOrder);
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
