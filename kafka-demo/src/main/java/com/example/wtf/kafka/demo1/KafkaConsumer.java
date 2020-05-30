package com.example.wtf.kafka.demo1;

import java.util.Optional;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author qingmei
 * @date 2020-05-22
 * @desc KafkaConsumer
 */
@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"test_wtf"})
    public void listener(ConsumerRecord record) {
        Optional<?> msg = Optional.ofNullable(record.value());
        if (msg.isPresent()) {
            System.out.println(msg.get());
        }
    }
}
