### DEMO 07A - RACE CONDITIONS

A race condition or race hazard is the condition of an electronics, software, or other system where the system's substantive behavior is dependent on the sequence or timing of other uncontrollable events.

This program illustrates race condition: Each time you run the program, the results displayed are different.

&nbsp;

### DEMO 07B - DATA RACES

Data race specifically refers to the non-synchronized conflicting "memory accesses" (or actions, or operations) to the same memory location.

I use a problem statement for illustration: From range [ 1..N ], count numbers of integers which are divisible by 2 or by 3.

For an example of N = 8, the integers that match the requirements are 2, 3, 6 and 8. Hence, the result is four numbers.

Of course, you can easily solve the problem by a single loop. However, we need to go deeper. There is another solution using "marking array".

- Let `a` be array of boolean values. Initialize all elements in `a` by `false`.
- For `i` in range `1` to `N`, if `i` is divisible by `2` or by `3`, then assign `a[i] = true`.
- Finally, the result is now counting number of `true` in `a`.

With `N = 8`, `a[2], a[3], a[4], a[6], a[8]` are marked by `true`.

&nbsp;

About the source code, there are two versions:

- Version 01 uses traditional single threading to let you get started.

- Version 02 uses multithreading.
    - Thread `markDiv2` will mark `true` for all numbers divisible by 2.
    - Thread `markDiv3` will mark `true` for all numbers divisible by 3.
    - **The rule of threading tell us that `a[6]` might be accessed by both threads at the same time ==> DATA RACE**. However, in the end, `a[6] = true` is obvious, so the final result is still correct ==> not race condition.

&nbsp;

### DEMO 07C - RACE CONDITIONS AND DATA RACES

Many people are confused about race conditions and data races.

- There is a case that race condition appears without data race. That is demo version A.
- There is also a case that data race appears without race condition. That is demo version B.

*Small note: Looking from a deeper perspective, demo version A still causes data race (that is... output console terminal, hahaha).*

Ususally, race condition happens together with data race. A race condition often occurs when two or more threads need to perform operations on the same memory area (data race) but the results of computations depends on the order in which these operations are performed.

Concurrent accesses to shared resources can lead to unexpected or erroneous behavior, so parts of the program where the shared resource is accessed need to be protected in ways that avoid the concurrent access. This protected section is the **critical section** or **critical region**.

&nbsp;
&nbsp;

### IMPORTANT NOTES

&nbsp;

Multithreading makes things run in parallel/concurrency. Therefore we need techniques that handle the control flow to:

- make sure the app runs correctly, and
- avoid race conditions.

There are several techniques, which are divided into two types: synchronization and non-synchronization.

|             | SYNCHRONIZATION | NON-SYNCHRONIZATION |
| ----------- | --------------- | ------------------- |
| Description | To block threads until a condition is satisfy | Not to block threads |
| Other names | Blocking | Non-blocking, lock-free |
| Techniques  | - Low-level: Mutex, semaphore, condition variable<br>- High-level: Synchronized block, blocking queue, barrier and latch | Atomic, thread-local storage |
| Pros        | - Give you in-depth controls<br>- Cooperate among threads | - App's performance may be better (compared to synchronization)<br>- Avoid deadlock |
| Cons        | - Hard to control in complex synchronization<br>- May be dangerous (when deadlock appears) | Usually too simple |

&nbsp;

Please note that we cannot replace sync techniques by non-sync techniques and vice versa. Each technique has its use cases, strengths and weaknesses.

In practical:

- We mostly use synchronized blocks, blocking queues, atomic operations and mutexes.
- We usually combine sync and non-sync techniques.

&nbsp;
