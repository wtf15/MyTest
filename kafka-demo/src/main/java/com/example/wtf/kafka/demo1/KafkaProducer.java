package com.example.wtf.kafka.demo1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author qingmei
 * @date 2020-05-22
 * @desc KafkaProducer
 */
@Component
public class KafkaProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send() {
        kafkaTemplate.send("test_wtf", "msgKey", "msgData");
    }
}
