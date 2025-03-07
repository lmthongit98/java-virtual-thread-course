package com.tma.training.demo12_virtual_thread.demo04;

import com.tma.training.util.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

/*
    Virtual Threads are indented for I/O tasks. This is a simple demo the use of ReentrantLock
 */
public class Demo04ReentrantLock {

    private static final Logger log = LoggerFactory.getLogger(Demo04ReentrantLock.class);
    private static final Lock lock = new java.util.concurrent.locks.ReentrantLock();
    private static final List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {

        demo(Thread.ofVirtual());

        CommonUtils.sleep(Duration.ofSeconds(2));

        log.info("list size: {}", list.size());
    }

    private static void demo(Thread.Builder builder){
        for (int i = 0; i < 50; i++) {
            builder.start(() -> {
                log.info("Task started. {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    inMemoryTask();
                }
                log.info("Task ended. {}", Thread.currentThread());
            });
        }
    }

    private static void inMemoryTask(){
        try{
            lock.lock();
            list.add(1);
        }catch (Exception e){
            log.error("error", e);
        }finally {
            lock.unlock();
        }
    }

}
