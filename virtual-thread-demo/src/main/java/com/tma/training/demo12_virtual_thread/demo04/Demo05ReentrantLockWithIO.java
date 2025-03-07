package com.tma.training.demo12_virtual_thread.demo04;

import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
    Virtual Threads are indented for I/O tasks.
    To avoid pinning with synchronized, use ReentrantLock
    When a virtual thread attempts to acquire a ReentrantLock and the lock is unavailable, the virtual thread will unmount from the platform thread
 */
public class Demo05ReentrantLockWithIO {

    private static final Logger log = LoggerFactory.getLogger(Demo05ReentrantLockWithIO.class);
    private static final Lock lock = new ReentrantLock();
    private static final int NUMBER_OF_CPU_CORES = CommonUtils.getNumberOfCpuCores();

    static {
//        System.setProperty("jdk.tracePinnedThreads", "short");
    }

    public static void main(String[] args) {

        Runnable runnable = () -> log.info("*** Test Message ***");

        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);

        CommonUtils.sleep(Duration.ofSeconds(15));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < NUMBER_OF_CPU_CORES; i++) {
            builder.start(Demo05ReentrantLockWithIO::ioTask);
        }
    }

    private static void ioTask(){
        try{
            lock.lock();
            log.info("Task started. {}", Thread.currentThread());
            CommonUtils.sleep(Duration.ofSeconds(5));
            log.info("Task ended. {}", Thread.currentThread());
        }catch (Exception e){
            log.error("error", e);
        }finally {
            lock.unlock();
        }
    }

}
