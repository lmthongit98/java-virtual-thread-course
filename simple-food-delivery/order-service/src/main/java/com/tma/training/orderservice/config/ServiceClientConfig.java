package com.tma.training.orderservice.config;

import com.tma.training.orderservice.clients.RestaurantServiceClient;
import com.tma.training.orderservice.clients.RiderServiceClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.util.concurrent.Executors;

@Configuration
public class ServiceClientConfig {

    @Value("${spring.threads.virtual.enabled}")
    private boolean isVirtualThreadEnabled;

    @Bean
    public RestaurantServiceClient restaurantServiceClient(@Value("${restaurant.service.url}") String baseUrl) {
        return new RestaurantServiceClient(buildRestClient(baseUrl));
    }

    @Bean
    public RiderServiceClient riderServiceClient(@Value("${rider.service.url}") String baseUrl) {
        return new RiderServiceClient(buildRestClient(baseUrl));
    }

    private RestClient buildRestClient(String baseUrl) {
        var builder = RestClient.builder().baseUrl(baseUrl);
        if (isVirtualThreadEnabled) {
            builder = builder.requestFactory(new JdkClientHttpRequestFactory(
                    HttpClient.newBuilder().executor(Executors.newVirtualThreadPerTaskExecutor()).build()
            ));
        }
        return builder.build();
    }

}
