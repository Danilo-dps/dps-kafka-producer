package com.danilodps.kakfaproducer.producer;

import com.danilodps.kakfaproducer.record.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    @Value("${spring.kafka.producer.topic.kafka-topic}")
    private String producerTopic;

    private final KafkaTemplate <String, Object> kafkaTemplate;

    public KafkaProducer(@Qualifier("producer")KafkaTemplate <String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserResponse userResponse){
        log.info("Enviando usuário: {}", userResponse.userId());
        kafkaTemplate.send(producerTopic, userResponse.userId(), userResponse);
    }
}
