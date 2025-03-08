package com.tma.training.demo12_virtual_thread.demo05.concurrencylimit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

// similar to proxy design pattern
public class ConcurrencyLimiter implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private final ExecutorService executorService;
    private final Semaphore semaphore;


    public ConcurrencyLimiter(ExecutorService executorService, int limit) {
        this.executorService = executorService;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        return executorService.submit(() -> wrapCallable(callable));
    }

    private <T> T wrapCallable(Callable<T> callable) {
        try {
            semaphore.acquire();
            return callable.call();
        } catch (Exception e) {
            log.error("error", e);
        }
        finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executorService.close();
    }
}
