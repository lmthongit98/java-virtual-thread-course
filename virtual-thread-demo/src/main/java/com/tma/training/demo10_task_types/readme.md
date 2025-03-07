# DEMO 10 - Task Types: CPU-Bound vs. IO-Bound & Ideal Thread Pool Size

## Overview
In concurrent programming, tasks can be categorized into **CPU-bound** and **IO-bound**. The efficiency of thread pools depends on selecting the right pool size based on the task type.

## Task Types
### 1. **CPU-Bound Tasks**
- These tasks require intensive computations and rely heavily on CPU processing power.
- Example workloads: mathematical calculations, cryptographic hashing, data compression, machine learning computations.
- The execution time is mostly spent on CPU cycles rather than waiting for external resources.

### 2. **IO-Bound Tasks**
- These tasks involve waiting for I/O operations such as network requests, database queries, file system access, or disk reads/writes.
- Example workloads: REST API calls, logging, file downloads, database queries.
- The execution time is mostly spent waiting rather than using CPU cycles.

## Choosing the Ideal Thread Pool Size
The optimal thread pool size depends on the nature of the workload.

### **1. CPU-Bound Tasks**
- **Formula:** `Number of CPU Cores + 1`
- **Why?** More threads than cores lead to excessive context switching, reducing performance.
- **Example:**
  ```java
  int cores = Runtime.getRuntime().availableProcessors();
  ExecutorService cpuBoundPool = Executors.newFixedThreadPool(cores + 1);
  ```

### **2. IO-Bound Tasks**
- **Formula:** `Number of CPU Cores * 2` (or higher depending on I/O latency)
- **Why?** Since most of the time is spent waiting for I/O, we can have more threads to utilize CPU efficiently.
- **Example:**
  ```java
  int cores = Runtime.getRuntime().availableProcessors();
  ExecutorService ioBoundPool = Executors.newFixedThreadPool(cores * 2);
  ```
- A better approach is to use a **custom ThreadPoolExecutor**:
  ```java
  int cores = Runtime.getRuntime().availableProcessors();
  ExecutorService ioBoundPool = new ThreadPoolExecutor(
      cores * 2, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
  ```

## Summary Table
| Task Type  | Formula |
|------------|------|
| CPU-Bound  | `CPU cores + 1` |
| IO-Bound   | `CPU cores * 2` or more |

## Best Practices
- **Profile your application** to determine whether tasks are CPU-bound or IO-bound.
- **Use thread pool monitoring** to adjust the thread pool size dynamically.
- **Avoid unbounded thread pools** to prevent excessive resource consumption.
- **Use asynchronous programming (CompletableFuture, Reactor, etc.)** for highly IO-bound workloads.

## Conclusion
Understanding task types helps in selecting the right thread pool size, minimizing context switching overhead, and optimizing system performance. Choosing an appropriate pool size based on whether tasks are CPU-bound or IO-bound leads to efficient resource utilization and improved scalability.

---
For more details, refer to the [Java Concurrency Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/concurrent/ExecutorService.html).
