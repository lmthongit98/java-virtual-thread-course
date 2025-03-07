package com.tma.training.demo12_virtual_thread.demo05.aggregator;

import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;

import java.util.concurrent.ExecutorService;

public class ConcurrentAggregatorService {

    private final ExecutorService executorService;

    public ConcurrentAggregatorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public ProductDto getProductDto(int id) throws Exception {
        var product = executorService.submit(() -> Client.getProduct(id));
        var rating = executorService.submit(() -> Client.getRating(id));
        return new ProductDto(id, product.get(), rating.get());
    }


}
