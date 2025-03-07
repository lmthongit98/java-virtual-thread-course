### DEMO 05 - THREAD DETACHING

When a thread is marked as a daemon, it means that it will not keep the JVM alive on its own.
The JVM will terminate automatically once all non-daemon (user) threads complete, regardless of whether daemon threads are still running.

"Detaching" Concept:
By making thFoo a daemon thread, it becomes "detached" from the normal lifecycle of user threads. It can run independently,
but the JVM will not wait for it to finish. When all user threads (including the main thread) terminate, the JVM stops all daemon threads automatically.
This is similar to the "fire-and-forget" behavior seen in thread detaching in other languages or frameworks.

&nbsp;