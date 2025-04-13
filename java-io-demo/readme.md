# 📡 Java Echo Server – 4 Versions Compared

This project demonstrates four versions of a simple **Echo Server** in Java using different I/O and concurrency models.

Each server listens on port `12345` and echoes back any input from the client with the prefix:  
`Echo: <message>`

---

## 📂 Implemented Versions

| Version | File Name | Java Version | Thread Model | I/O Type | Scalability | Complexity |
|--------|------------|--------------|---------------|----------|-------------|-------------|
| 1️⃣ Blocking Single-threaded | `BlockingSingleThreadedEchoServer.java` | Java 8+ | One thread handles all clients sequentially | Blocking | ❌ Very Poor | ✅ Very Simple |
| 2️⃣ Blocking Multi-threaded | `BlockingMultiThreadedEchoServer.java` | Java 8+ | One thread per client | Blocking | ⚠️ Moderate | ✅ Simple |
| 3️⃣ Virtual Threads | `VirtualThreadEchoServer.java` | Java 21+ | One virtual thread per client | Blocking (simplified) | ✅ Excellent | ✅ Simple |
| 4️⃣ Non-blocking NIO | `NonBlockingEchoServer.java` | Java 8+ | One thread handles all I/O via `Selector` | Non-blocking | ✅ Excellent | ⚠️ Complex |

---

## ✅ How to Test The Server

1. **Connect to the server** by running the following command:
   ```bash
   nc localhost 12345 
   ```
2. Type a message and press enter. For example:
`Hello World`
3. Observe the server’s response. Based on the example, the server echoes the message: `Echo: Hello World`
4. Close the connection by pressing `Ctrl + D` (EOF) or `Ctrl + C` (interrupt).

**You can open multiple terminals with nc to simulate multiple clients.**





