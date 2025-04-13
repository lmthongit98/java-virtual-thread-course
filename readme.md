# Java Virtual Thread Training Course

This repository is designed to support a hands-on training course on **Java Virtual Threads** (Project Loom), with a focus on understanding blocking vs. non-blocking I/O, thread models, and benchmarking real-world microservice performance using platform threads and virtual threads.

---

## 📚 Course Overview

This course is divided into three practical modules:

### 1. `java-io-demo`
Illustrates the difference between **blocking** and **non-blocking** I/O using classic Java IO and Java NIO (non-blocking channels).

> ✅ Learn how I/O operations block threads and how non-blocking I/O can improve throughput.

### 2. `virtual-thread-demo`
Explores thread usage in Java with multiple examples:
- Platform Threads
- Virtual Threads
- Structured Concurrency

> ✅ Understand how virtual threads simplify concurrency by reducing the cost of thread creation and context switching.

### 3. `simple-food-delivery`
A minimal microservice project to **benchmark performance** using:
- Platform threads (traditional thread pool)
- Virtual threads (from Project Loom)

> ✅ Measure response time, throughput, and resource consumption to understand the performance implications.

---

## 🧠 Learning Objectives

By completing this course, you will:
- Understand how traditional I/O blocks threads and impacts scalability.
- Learn how Java NIO enables non-blocking I/O using selectors and channels.
- Gain practical experience with platform vs. virtual thread programming models.
- Evaluate the suitability of virtual threads for high-concurrency applications.
- Learn benchmarking practices using a microservice simulation.

