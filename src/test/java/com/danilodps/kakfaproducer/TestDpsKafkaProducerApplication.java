package com.danilodps.kakfaproducer;

import org.springframework.boot.SpringApplication;

public class TestDpsKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.from(DpsKafkaProducerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
