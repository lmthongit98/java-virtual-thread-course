## DEMO 05 - THREAD STATES

In Java, a thread can exist in one of several states throughout its lifecycle. The `Thread.State` enum defines the possible states a thread can be in. This document provides an overview of these states and how threads transition between them.

## Thread States
### 1. **NEW**
- A thread that has been created but not yet started.
- Example:
  ```java
  Thread t = new Thread(() -> System.out.println("Hello"));
  ```
- Transition:
    - `NEW -> RUNNABLE` when `start()` is called.

### 2. **RUNNABLE**
- A thread that is ready to run and is waiting for CPU allocation.
- In Java, there is no explicit **RUNNING** state; a thread in the **RUNNABLE** state can be chosen by the scheduler to run.
- Example:
  ```java
  t.start(); // Now the thread is RUNNABLE
  ```
- Transition:
    - `RUNNABLE -> BLOCKED/WAITING/TIMED_WAITING` if it needs to wait.
    - `RUNNABLE -> TERMINATED` if execution completes.
    - When moving from **RUNNABLE** to a blocked state, **context switching occurs**, leading to **overhead**.

### 3. **BLOCKED**
- A thread that is waiting to acquire a monitor lock to enter a synchronized block/method.
- Example:
  ```java
  synchronized (lock) {
      // Another thread is holding the lock
  }
  ```
- Transition:
    - `BLOCKED -> RUNNABLE` when the lock is released.

### 4. **WAITING**
- A thread that is waiting indefinitely for another thread to signal it.
- Example:
  ```java
  synchronized (lock) {
      lock.wait();
  }
  ```
- Transition:
    - `WAITING -> RUNNABLE` when `notify()` or `notifyAll()` is called.

### 5. **TIMED_WAITING**
- A thread that is waiting for a specified time.
- Example:
  ```java
  Thread.sleep(1000); // Sleeps for 1 second
  ```
- Other scenarios:
    - `wait(timeout)`, `join(timeout)`, `LockSupport.parkNanos()`, `LockSupport.parkUntil()`.
- Transition:
    - `TIMED_WAITING -> RUNNABLE` when the timeout expires.

### 6. **TERMINATED**
- A thread that has completed execution or has been stopped.
- Example:
  ```java
  class MyThread extends Thread {
      public void run() {
          System.out.println("Thread completed");
      }
  }
  MyThread t = new MyThread();
  t.start();
  t.join(); // Ensures main thread waits for completion
  ```
- Transition:
    - The thread cannot transition out of this state.

## Thread State Transitions
| Current State | Possible Next States |
|--------------|---------------------|
| NEW | RUNNABLE |
| RUNNABLE | BLOCKED, WAITING, TIMED_WAITING, TERMINATED |
| BLOCKED | RUNNABLE |
| WAITING | RUNNABLE |
| TIMED_WAITING | RUNNABLE |
| TERMINATED | (End state) |

## Context Switching and Overhead
- **When a task moves from RUNNABLE to a blocked state (e.g., waiting for I/O or a lock), context switching occurs, causing overhead.**
---

For more details, refer to the [Java Thread Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Thread.State.html).