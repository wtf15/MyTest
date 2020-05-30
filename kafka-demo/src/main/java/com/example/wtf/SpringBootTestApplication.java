package com.example.wtf;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.wtf.kafka.demo1.KafkaProducer;

@SpringBootApplication
public class SpringBootTestApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootTestApplication.class, args);
        KafkaProducer kafkaProducer = context.getBean(KafkaProducer.class);
        for (int i = 0; i < 10; i++) {
            kafkaProducer.send();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
