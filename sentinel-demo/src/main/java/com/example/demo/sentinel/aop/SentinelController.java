package com.example.demo.sentinel.aop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

@RestController
public class SentinelController {

    // 针对方法级别的限流
    @SentinelResource(value = "sayHello")
    @GetMapping("/say")
    public String sayHello() {
        System.out.println("hello world");
        return "hello world";
    }
}
