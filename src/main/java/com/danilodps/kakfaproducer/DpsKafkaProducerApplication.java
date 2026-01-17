package com.danilodps.kakfaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class DpsKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpsKafkaProducerApplication.class, args);
    }

}
