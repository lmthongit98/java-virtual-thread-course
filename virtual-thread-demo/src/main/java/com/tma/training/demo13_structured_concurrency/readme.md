# **Structured Concurrency in Java 21 (Preview Feature)**

## **Introduction**
Structured Concurrency in Java 21 is a modern concurrency approach that simplifies handling multiple tasks by ensuring that all subtasks complete within a well-defined scope. It prevents common concurrency issues such as orphan threads, resource leaks, and difficult debugging.

## **Key Features**
- **Task Scope Management**: Ensures that all child tasks complete before exiting the scope.
- **Automatic Cancellation**: Cancels remaining tasks if one fails (`ShutdownOnFailure`) or stops unnecessary tasks once one succeeds (`ShutdownOnSuccess`).
- **Simplified Error Handling**: Exceptions from subtasks are automatically propagated.
- **Better Debugging**: Stack traces clearly show task hierarchy.
- **Works Well with Virtual Threads**: Improves performance and scalability.

## **StructuredTaskScope API Overview**
| Method | Description |
|--------|-------------|
| `fork(Supplier<T>)` | Starts a new subtask and returns a `Subtask<T>` object |
| `join()` | Waits for all tasks to complete |
| `throwIfFailed()` | Propagates exceptions from any failed subtask |
| `shutdown()` | Cancels all running tasks |
| `shutdownOnFailure()` | Cancels all tasks if one fails |
| `shutdownOnSuccess()` | Cancels remaining tasks once one succeeds |

## **Usage Scenarios**
- **Fetching data from multiple sources (failover mechanism)**
- **Parallel execution of independent tasks**
- **Running multiple API calls and handling failures efficiently**
- **Improving system responsiveness by stopping unnecessary work**

## **Example 1: Basic Structured Concurrency**
```java
import java.util.concurrent.*;

public class StructuredConcurrencyExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> fetchData("Task 1"));
            var task2 = scope.fork(() -> fetchData("Task 2"));

            scope.join(); // Waits for all tasks
            scope.throwIfFailed(); // Throws exception if any task fails

            System.out.println("Results: " + task1.get() + ", " + task2.get());
        }
    }

    private static String fetchData(String name) {
        return name + " completed";
    }
}
```

## **Example 2: Handling Failures with `ShutdownOnFailure`**
```java
import java.util.concurrent.*;

public class FailureHandlingExample {
    public static void main(String[] args) {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var task1 = scope.fork(() -> fetchData("Task 1"));
            var task2 = scope.fork(() -> { throw new RuntimeException("Task 2 failed!"); });

            scope.join();
            scope.throwIfFailed(); // Ensures the failure is properly handled
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("Error caught: " + e.getMessage());
        }
    }

    private static String fetchData(String name) {
        return name + " completed";
    }
}
```

## **Example 3: Fastest Successful Task with `ShutdownOnSuccess`**
```java
import java.util.concurrent.*;

public class FastestTaskExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            var task1 = scope.fork(() -> fetchData("Task 1", 3000));
            var task2 = scope.fork(() -> fetchData("Task 2", 1000));
            var task3 = scope.fork(() -> fetchData("Task 3", 2000));

            scope.join();
            System.out.println("First successful task: " + scope.result());
        }
    }

    private static String fetchData(String name, int delay) throws InterruptedException {
        Thread.sleep(delay);
        return name + " completed";
    }
}
```

## **Best Practices**
- Use **`ShutdownOnFailure`** when all tasks must succeed for the operation to be meaningful.
- Use **`ShutdownOnSuccess`** when only one successful result is needed, canceling slower tasks.
- Always call **`throwIfFailed()`** after `join()` when using `ShutdownOnFailure` to properly handle errors.
- Combine with **virtual threads** for maximum performance and scalability.

## **Structured concurrency and Java 21’s Virtual Threads**
JEP 453 summarizes very well how structured concurrency is an excellent complement to virtual threads:

    virtual threads deliver an abundance of threads. Structured concurrency can correctly and robustly coordinate them, and enables observability tools to display threads as they are understood by the developer

    The application might have millions of concurrent threads that must be correctly coordinated.

## **Conclusion**
Structured concurrency in Java 21 makes concurrent programming safer, cleaner, and more efficient by grouping related tasks together and ensuring their proper execution. By combining StructuredTaskScope with virtual threads, Java provides a highly scalable approach to concurrent programming that is easy to use and maintain.

---
🚀 **Adopt structured concurrency in your Java applications today for better performance and reliability!**
