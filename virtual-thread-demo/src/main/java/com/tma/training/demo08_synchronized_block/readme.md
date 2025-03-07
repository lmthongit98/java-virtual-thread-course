# DEMO 08 - Synchronized in Java

## Overview
The `synchronized` keyword in Java is used for thread synchronization to ensure that multiple threads do not interfere with each other when accessing shared resources. It prevents race conditions by allowing only one thread to execute a synchronized block or method at a time.

## Why Synchronization?
- **Concurrency issues**: Without synchronization, multiple threads may modify shared resources inconsistently.
- **Thread safety**: Ensures that critical sections are executed atomically.
- **Avoiding race conditions**: Prevents multiple threads from corrupting data.
- **Mutex and Monitor**: Java uses object monitors for synchronization, and each synchronized block must acquire a monitor lock before executing.

## Types of Synchronization

### 1. **Method Synchronization**
A method can be marked as `synchronized` to ensure that only one thread can execute it at a time.

#### Example:
```java
class SharedResource {
    public synchronized void syncMethod() {
        System.out.println(Thread.currentThread().getName() + " is executing synchronized method");
    }
}
```
- Locks on the instance of the object (`this`).
- If the method is `static`, it locks on the **class object** (`ClassName.class`).

### 2. **Block Synchronization**
A specific section of code can be synchronized instead of the entire method.

#### Example:
```java
class SharedResource {
    public void syncBlock() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " is executing synchronized block");
        }
    }
}
```
- Only locks the specified block instead of the whole method, improving concurrency.
- **Best practice** when only a small section of code needs synchronization.

### 3. **Static Synchronization**
When synchronizing a `static` method or block, the lock is obtained on the **class object**, ensuring that all instances of the class share the same lock.

#### Example:
```java
class SharedResource {
    public static synchronized void staticSyncMethod() {
        System.out.println(Thread.currentThread().getName() + " is executing static synchronized method");
    }
}
```
- Useful for synchronizing shared class-level resources.

## Synchronization Overhead & Context Switching
- **Thread blocking**: When a thread enters a synchronized block, others attempting to access the same resource are blocked.
- **Context switching overhead**: Switching between threads involves saving/restoring execution states, adding performance overhead.
- **Potential deadlocks**: Improper synchronization can lead to deadlocks where threads indefinitely wait for each other.

## Alternative to Synchronization
- **ReentrantLock (java.util.concurrent.locks)**: Provides finer control over locks.
- **Atomic Variables (java.util.concurrent.atomic)**: Useful for simple atomic operations without full synchronization.
- **Volatile Keyword**: Ensures visibility of variables across threads but does not provide atomicity.

## Best Practices
- Minimize the scope of synchronization to reduce contention.
- Avoid synchronizing on mutable objects to prevent unexpected behavior.
- Use `ConcurrentHashMap` and `CopyOnWriteArrayList` for thread-safe collections instead of synchronizing manually.

## Conclusion
The `synchronized` keyword is a simple and effective way to ensure thread safety, but it introduces overhead. Understanding when and where to use it properly helps in writing efficient concurrent applications.

---
For more details, refer to the [Java Concurrency Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html#synchronized).
