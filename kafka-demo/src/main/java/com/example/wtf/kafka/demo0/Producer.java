package com.example.wtf.kafka.demo0;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author qingmei
 * @date 2020-05-22
 * @desc Producer
 */
public class Producer extends Thread {
    private final KafkaProducer<Integer, String> producer;
    private final String topic;

    public Producer(String topic) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "172.16.107.150:9092,172.16.107.151:9092,172.16.107.152:9092");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "practice-producer");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG,"com.example.wtf.kafka.demo2.MyPartitioner");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        producer = new KafkaProducer<Integer, String>(properties);
        this.topic = topic;
    }

    public static void main(String[] args) {
        new Producer("test").start();
    }

    /**
     * 同步发送
     */
    @Override
    public void run() {
        int num = 0;
        while (num < 50) {
            String msg = "pratice test message:" + num;
            try {
                producer.send(new ProducerRecord<Integer, String>(topic, msg)).get();
                TimeUnit.SECONDS.sleep(2);
                num++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 异步发送
     */
    /*@Override
    public void run() {
        int num = 0;
        while (num < 50) {
            String msg = "pratice test message:" + num;
            try {
                producer.send(new ProducerRecord<>(topic, msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        System.out.println("callback: " + recordMetadata.offset() + "->" + recordMetadata.partition());
                    }
                });
                TimeUnit.SECONDS.sleep(2);
                num++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }*/

}
