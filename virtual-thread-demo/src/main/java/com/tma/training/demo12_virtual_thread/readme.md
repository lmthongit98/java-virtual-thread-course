# Virtual Threads in Java 21

## Overview
Virtual Threads, introduced in **Java 21** as part of **Project Loom**, are **lightweight threads** that dramatically improve the scalability of concurrent applications. Unlike platform threads, which are managed by the OS, virtual threads are managed by the **JVM scheduler**, reducing **memory usage**, **context switching overhead**, and **thread management complexity**.

## Why Use Virtual Threads?
### **1. Efficiency Compared to Platform Threads**
- **Lower Memory Consumption** – A virtual thread consumes **kilobytes** of memory, whereas a platform thread consumes **megabytes**.
- **Millions of Threads** – Virtual threads allow **millions of concurrent tasks**, compared to thousands with platform threads.
- **No Kernel Context Switching** – The JVM handles scheduling, avoiding costly OS context switching.
- **Faster Creation & Disposal** – Virtual threads can be created and destroyed with minimal overhead.

### **2. Simplifies Concurrency**
- **Uses Familiar Java API** – Virtual threads use the same `Thread` API, making it easy to adopt.
- **Seamless Integration** – Works with `Executors`, `StructuredTaskScope`, and Java's standard concurrency primitives.
- **No Need for Thread Pools** – Since virtual threads are lightweight, the traditional need for managing thread pools is reduced.

## Ways to Create Virtual Threads in Java 21

### **1. Using `Thread.ofVirtual().start()` (Simplest Way)**
```java
Thread thread = Thread.ofVirtual().start(() -> {
    System.out.println("Running in virtual thread: " + Thread.currentThread());
});
```
- Creates and **immediately starts** a virtual thread.
- Equivalent to `new Thread().start()` but for virtual threads.

### **2. Using `Thread.ofVirtual().unstarted()` (Manually Start Later)**
```java
Thread thread = Thread.ofVirtual().unstarted(() -> {
    System.out.println("Virtual thread started later: " + Thread.currentThread());
});
thread.start();
```
- Creates a virtual thread **without starting it immediately**.
- Useful for controlling execution timing.

### **3. Using `Executors.newVirtualThreadPerTaskExecutor()` (Recommended for Multiple Tasks)**
```java
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 10; i++) {
        executor.submit(() -> {
            System.out.println("Task executed in virtual thread: " + Thread.currentThread());
        });
    }
}
```
- Creates an **executor service** where **each task gets its own virtual thread**.
- **Auto-closes** after execution using try-with-resources.

### **4. Using `Executors.newThreadExecutor(Thread.ofVirtual())` (Explicit Custom Executor)**
```java
ExecutorService executor = Executors.newThreadExecutor(Thread.ofVirtual());
executor.submit(() -> System.out.println("Virtual thread running in custom executor"));
executor.shutdown();
```
- Alternative to `newVirtualThreadPerTaskExecutor()`.
- Allows **customization** of executor behavior.

### **5. Using `StructuredTaskScope` (Scoped Execution)**
```java
import java.util.concurrent.*;

try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Future<String> future = scope.fork(() -> {
        return "Result from virtual thread: " + Thread.currentThread();
    });
    scope.join(); // Waits for all tasks to finish
    System.out.println(future.resultNow());
}
```
- **Structured concurrency**: Manages multiple virtual threads within a task scope.
- **Ensures proper shutdown** if any thread fails.

## How Virtual Threads Work
- **Virtual threads are NOT bound to platform threads** permanently.
- Instead, they **run on top of a small number of carrier threads**, which are platform threads.
- The JVM **automatically schedules** virtual threads on available carrier threads.

### **Key Characteristics**
| Feature | Virtual Threads | Platform Threads |
|---------|----------------|------------------|
| Memory Usage | Low (~KB per thread) | High (~MB per thread) |
| Context Switching | Managed by JVM | OS Kernel-level overhead |
| Concurrency | Supports millions of threads | Limited by system resources |
| Best For | High I/O, lightweight tasks | CPU-intensive workloads |
| Requires Thread Pools? | No | Yes |

## **When to Use Virtual Threads?**
✔ **Best suited for I/O-bound tasks** (e.g., database calls, network requests, file operations).  
❌ **Not ideal for CPU-bound tasks** (e.g., heavy computations) since they still share CPU time on platform threads.

## Best Practices
- **Use `Executors.newVirtualThreadPerTaskExecutor()`** for handling multiple tasks.
- **Avoid blocking** inside virtual threads using traditional `synchronized` locks, as it blocks the carrier thread.
- **Combine with `StructuredTaskScope`** for better error handling and lifecycle management.
- **Don't replace platform threads entirely** – Use them for CPU-heavy tasks and virtual threads for high-concurrency I/O workloads.

## **Performance Benefits of Virtual Threads**
| Feature | Benefit |
|---------|---------|
| **Reduced Memory Usage** | Virtual threads consume significantly less memory than platform threads. |
| **Minimal Context Switching Overhead** | JVM manages scheduling, reducing expensive OS-level context switches. |
| **Increased Scalability** | Can support millions of concurrent threads efficiently. |
| **Simplified Concurrency Model** | No need to manually manage thread pools. |

## Conclusion
Virtual threads in **Java 21** revolutionize concurrent programming, allowing developers to create **millions of lightweight threads** with minimal overhead. By integrating virtual threads with structured concurrency and executor services, Java applications can handle massive workloads efficiently.

---
### **Further Reading**
- [Project Loom Documentation](https://openjdk.org/projects/loom/)
- [Java Virtual Threads Guide](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html)