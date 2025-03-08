/*
   A simple demo of ShutdownOnSuccess where we want to cancel all the running subtasks when we get the first success response
*/

package com.tma.training.demo13_structured_concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class AppC {

    private static final Logger log = LoggerFactory.getLogger(AppC.class);

    public static void main(String[] args) {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            var task1 = scope.fork(() -> doTask("Task 1", 3000, false));
            var task2 = scope.fork(() -> doTask("Task 2", 1000, true));
            var task3 = scope.fork(() -> doTask("Task 3", 2000, false));

            scope.join(); // Waits for the first successful task

            log.info("task 1 state: {}", task1.state());
            log.info("task 2 state: {}", task2.state());
            log.info("task 3 state: {}", task3.state());

            log.info("First completed task: {}", scope.result());
        } catch (ExecutionException | InterruptedException e) {
            log.error("Error: {}", e.getMessage());
        }
    }

    private static String doTask(String name, int delay, boolean shouldFail) throws InterruptedException {
        log.info("Running {} ...", name);
        Thread.sleep(delay);

        if (shouldFail) {
            throw new RuntimeException(STR."\{name} encountered an error!");
        }

        return STR."\{name} finished";
    }

}
