package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

/*
   Virtual threads remove the need for pooling, but we still use ExecutorService for task management and obtaining Future<T> results.

   A simple demo to get multiple products information in parallel

 */
public class Demo02AccessResponseUsingFuture {

    private static final Logger log = LoggerFactory.getLogger(Demo02AccessResponseUsingFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var product1 = executor.submit(() -> Client.getProduct(1));
            var product2 = executor.submit(() -> Client.getProduct(2));
            var product3 = executor.submit(() -> Client.getProduct(3));

            log.info("product-1: {}", product1.get());
            log.info("product-2: {}", product2.get());
            log.info("product-3: {}", product3.get());
        }

    }

}
