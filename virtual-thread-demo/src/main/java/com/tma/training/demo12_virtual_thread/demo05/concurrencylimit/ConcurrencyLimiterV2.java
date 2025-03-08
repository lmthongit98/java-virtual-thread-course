package com.tma.training.demo12_virtual_thread.demo05.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.*;

// Version 2: guarantee the orders
public class ConcurrencyLimiterV2 implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiterV2.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;
    private final Queue<Callable<?>> queue;


    public ConcurrencyLimiterV2(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>(); // thread safe
    }

    public <T> Future<T> submit(Callable<T> callable) {
        this.queue.add(callable);
        return executorService.submit(() -> executeTask(callable));
    }

    private <T> T executeTask(Callable<T> callable) {
        try {
            semaphore.acquire();
            return (T) this.queue.poll().call();
        } catch (Exception e) {
            log.error("error", e);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
    }
}
