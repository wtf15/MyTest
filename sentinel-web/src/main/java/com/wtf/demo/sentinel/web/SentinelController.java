package com.wtf.demo.sentinel.web;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wtf.demo.sentinel.SentinelService;

@RestController
public class SentinelController {

    @Reference(timeout = 3000)
    SentinelService sentinelService;

    @GetMapping("/say")
    public String sayHello() {
        return sentinelService.sayHello("test");
    }

}
