package com.example.demo.sentinel;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterMain {

    // 令牌桶的实现
    RateLimiter rateLimiter = RateLimiter.create(10); // qps

    public static void main(String[] args) throws IOException {
        RateLimiterMain rateLimiterMain = new RateLimiterMain();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    Thread.sleep(random.nextInt(1000));
                    rateLimiterMain.doTest();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.countDown();
        System.in.read();
    }

    public void doTest() {
        // 这里就是获得一个令牌，成功获得了一个令牌
        if (rateLimiter.tryAcquire()) {
            System.out.println("允许通过进行访问");
        } else {
            System.out.println("被限流了");
        }
    }
}
