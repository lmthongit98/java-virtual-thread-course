### DEMO 06 - EXECUTOR SERVICES AND THREAD POOLS

## Overview
Thread creation in Java involves overhead due to system calls, kernel context switching, and platform thread limits. To mitigate this, Java provides the **Thread Pool Design Pattern** via `ExecutorService` to manage and reuse threads efficiently.

## ExecutorService
`ExecutorService` provides a higher-level API for managing threads:
- **`submit(Runnable/Callable)`** – Submits a task for execution.
- **`invokeAll(Collection<Callable>)`** – Executes multiple tasks and waits for all to finish.
- **`invokeAny(Collection<Callable>)`** – Executes multiple tasks and returns the result of the first completed task.
- **`shutdown()`** – Initiates an orderly shutdown.
- **`shutdownNow()`** – Attempts to stop all executing tasks immediately.

### Example:
```java
ExecutorService executor = Executors.newFixedThreadPool(3);
executor.submit(() -> System.out.println("Task executed by: " + Thread.currentThread().getName()));
executor.shutdown();
```

## Thread Pools
Thread pools optimize performance by reusing threads instead of creating new ones.

### 1. **FixedThreadPool**
- Limits the number of threads.
- Example:
  ```java
  ExecutorService fixedPool = Executors.newFixedThreadPool(4);
  ```

### 2. **CachedThreadPool**
- Dynamically creates/reuses threads.
- Best for short-lived, bursty tasks.
- Example:
  ```java
  ExecutorService cachedPool = Executors.newCachedThreadPool();
  ```

### 3. **SingleThreadExecutor**
- Ensures sequential execution of tasks.
- Best for tasks needing ordered execution and avoiding data races or race conditions.
- Example:
  ```java
  ExecutorService singlePool = Executors.newSingleThreadExecutor();
  ```

### 4. **ScheduledThreadPool**
- Schedules tasks to run after a delay or periodically.
- Example:
  ```java
  ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(2);
  scheduledPool.schedule(() -> System.out.println("Delayed Task"), 2, TimeUnit.SECONDS);
  ```

## Best Practices
- Always call `shutdown()` to release resources.
- Use **try-with-resources** (via `AutoCloseable`) to automatically shut down executors.
- Choose the appropriate thread pool based on workload to optimize resource usage.

## Conclusion
Using **ExecutorService** and **Thread Pools** reduces thread management overhead and improves concurrency. Properly selecting a thread pool is key to achieving optimal performance.

---

For more details, refer to the [Java Concurrency Documentation](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/concurrent/ExecutorService.html).
