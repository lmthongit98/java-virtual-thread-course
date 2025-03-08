/*
    Assume the scenario where findUser() takes a long time to run, but fetchOrder() failed fast.
    The getResponse() thread would unnecessarily wait for findUser() to complete instead of failing immediately.
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppA04 {

    private static final Logger log = LoggerFactory.getLogger(AppA04.class);

    record OrderDto(String user, Integer order) {
    }

    public static void main(String[] args) {
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            OrderDto orderDto = getResponse(executorService);
            log.info("orderDto {}", orderDto);
        } catch (ExecutionException | InterruptedException e) {
            log.error("Sub task exception caught", e);
        }
    }

    private static OrderDto getResponse(ExecutorService executorService) throws ExecutionException, InterruptedException {
        Future<String> user = executorService.submit(AppA04::findUser);
        Future<Integer> order = executorService.submit(AppA04::fetchOrder);

        String theUser = user.get(); // This still runs for a long time unnecessarily
        int theOrder = order.get(); // This fails quickly

        return new OrderDto(theUser, theOrder);
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
