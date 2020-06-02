package com.wtf.demo.sentinel.provider;

import java.time.LocalDateTime;

import org.apache.dubbo.config.annotation.Service;

import com.wtf.demo.sentinel.SentinelService;

/**
 * 把当前服务发布成dubbo服务
 */
@Service
public class SentinelServiceImpl implements SentinelService {

    @Override
    public String sayHello(String txt) {
        return "hello world :" + LocalDateTime.now();
    }
}
