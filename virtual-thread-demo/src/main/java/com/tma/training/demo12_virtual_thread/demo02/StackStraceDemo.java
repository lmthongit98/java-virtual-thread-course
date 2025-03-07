package com.tma.training.demo12_virtual_thread.demo02;

import com.tma.training.util.CommonUtils;

import java.time.Duration;

public class StackStraceDemo {

    public static void main(String[] args) {

        demo(Thread.ofVirtual().name("virtual-", 1));

        CommonUtils.sleep(Duration.ofSeconds(2));

    }

    private static void demo(Thread.Builder builder){
        for (int i = 1; i <= 20 ; i++) {
            int j = i;
            builder.start(() -> Task.execute(j));
        }
    }


}
