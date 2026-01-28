package com.danilodps.kakfaproducer;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

@ExtendWith(MockitoExtension.class)
public class TestDpsKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.from(DpsKafkaProducerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
