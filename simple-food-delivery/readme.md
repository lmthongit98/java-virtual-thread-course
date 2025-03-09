## Simple Food Delivery Project - Performance Testing with Virtual Threads vs. Platform Threads

### 📌 Project Overview

This project is a very simple microservices-based Spring Boot application designed to benchmark the performance of Java Virtual Threads against Platform Threads using JMeter load testing.

#### This project consists of three microservices:

- **Order Service** (order-service) - Handles food ordering.

- **Restaurant Service** (restaurant-service) - Assigns food preparation.

- **Rider Service** (rider-service) - Assigns delivery riders.

#### By running **JMeter** load tests on these services, we can compare:

- **Throughput** (Transactions Per Second - TPS)

- **Response Time** (Latency)

- **CPU & Memory Usage**

To enable virtual thread in Spring boot 3.2+, you just simple adding a configuration flag: `spring.threads.virtual.enabled=true`