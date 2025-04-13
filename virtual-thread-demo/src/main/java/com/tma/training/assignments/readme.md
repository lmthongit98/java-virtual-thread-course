## Objective
Build a Java application that fetches product names from a simulated remote service. The goal is to demonstrate the difference in performance between single-threaded, multithreaded, and virtual thread implementations.

---

## Requirements

### Input:
- A list of product IDs (hardcoded or randomly generated).

### Output:
- A list of product names corresponding to the given product IDs.

### Implementations:

#### Version 1: Single-Threaded Implementation
- Fetch product names sequentially.
- Measure the time taken for the entire process.

#### Version 2: Multithreaded Implementation
- Fetch product names concurrently using threads.
- Use a **Thread Pool** (via `ExecutorService`) to manage threads.
- Find the ideal pool size
- Measure the time taken and compare it with the previous version.

#### Version 3: Virtual Thread Implementation
- Utilize **Virtual Threads** from Project Loom to reduce thread management overhead.
- Launch virtual threads instead of traditional threads.
- Measure the time taken and compare it with the previous version.
---

## Performance Measurement
- Use `System.currentTimeMillis()` to log start and end times for each implementation.
- Print the results in a clear and comparable format.

## Simulation of Network I/O
- Use a `HashMap` to store product IDs and names.
- Use `Thread.sleep(1000)` to simulate network latency.

