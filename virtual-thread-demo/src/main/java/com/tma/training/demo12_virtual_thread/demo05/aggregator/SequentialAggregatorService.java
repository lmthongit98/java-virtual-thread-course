package com.tma.training.demo12_virtual_thread.demo05.aggregator;

import com.tma.training.demo12_virtual_thread.demo05.externalservice.Client;

public class SequentialAggregatorService {

    public ProductDto getProductDto(int id) {
        return new ProductDto(id, Client.getProduct(id), Client.getRating(id));
    }

}
