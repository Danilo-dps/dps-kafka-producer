package com.danilodps.kakfaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@EnableAsync
@EnableKafka
@SpringBootApplication
public class DpsKafkaProducerApplication {

    private DpsKafkaProducerApplication(){}
    static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
        SpringApplication.run(DpsKafkaProducerApplication.class, args);
    }

}
