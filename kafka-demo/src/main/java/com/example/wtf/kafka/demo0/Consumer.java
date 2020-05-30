package com.example.wtf.kafka.demo0;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author qingmei
 * @date 2020-05-22
 * @desc Consumer
 */
public class Consumer extends Thread {
    private final KafkaConsumer<Integer, String> consumer;
    private final String topic;

    public Consumer(String topic) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "172.16.107.150:9092,172.16.107.151:9092,172.16.107.152:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "practice-consumer");
        // 设置 offset自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        // 自动提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
            "org.apache.kafka.common.serialization.StringDeserializer");
        // 对于 当前groupid来说，消息的offset从最早的消息开始消费
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumer = new KafkaConsumer<>(properties);
        this.topic = topic;
    }

    public static void main(String[] args) {
        new Consumer("test").start();
    }

    @Override
    public void run() {
        while (true) {
            consumer.subscribe(Collections.singleton(this.topic));
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(1));
            records.forEach(record -> {
                System.out.println(record.key() + " " + record.value() + " ->offset:" + record.offset());
            });
        }
    }
}
