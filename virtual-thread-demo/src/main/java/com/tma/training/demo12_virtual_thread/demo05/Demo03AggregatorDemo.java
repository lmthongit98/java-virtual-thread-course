package com.tma.training.demo12_virtual_thread.demo05;

import com.tma.training.demo12_virtual_thread.demo05.aggregator.ConcurrentAggregatorService;
import com.tma.training.demo12_virtual_thread.demo05.aggregator.ProductDto;
import com.tma.training.demo12_virtual_thread.demo05.aggregator.SequentialAggregatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/*
*
*   In a microservices architecture, it is common to have a service that aggregates data from multiple services and returns the combined result to the frontend.
*
*   To minimize latency, the application sends source requests concurrently, allowing them to execute simultaneously.
*   This significantly reduces response time, as explained below:
*
*   Latency of concurrent requests = max(latency(source1), latency(source2), ..., latency(source_n))
*   (The total latency is determined by the slowest request.)
*
*   Latency of sequential requests = latency(source1) + latency(source2) + ... + latency(source_n)
*   (Each request must wait for the previous one to complete, leading to higher overall latency.)
*
*
*/

public class Demo03AggregatorDemo {

    private static final Logger log = LoggerFactory.getLogger(Demo03AggregatorDemo.class);

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        List<ProductDto> products = getProductsConcurrently();

        log.info("list of product: {}", products);
        log.info("Total time {}", System.currentTimeMillis() - start);
    }

    private static List<ProductDto> getProductsConcurrently() {
        try (var executor = Executors.newThreadPerTaskExecutor(Thread.ofVirtual().name("routine-", 1).factory())) {
            var concurrentAggregator = new ConcurrentAggregatorService(executor);

            List<Future<ProductDto>> futures = IntStream.rangeClosed(1, 50)
                    .mapToObj(i -> executor.submit(() -> concurrentAggregator.getProductDto(i)))
                    .toList();

            List<ProductDto> products = futures.stream().map(Demo03AggregatorDemo::toProductDto).toList();
            return products;
        }
    }

    private static List<ProductDto> getProductsSequentially() {
        var sequentialAggregator = new SequentialAggregatorService();
        List<ProductDto> products = IntStream.rangeClosed(1, 50).mapToObj(sequentialAggregator::getProductDto).toList();
        return products;
    }

    private static ProductDto toProductDto(Future<ProductDto> future) {
        try {
            return future.get(); // blocking call
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
