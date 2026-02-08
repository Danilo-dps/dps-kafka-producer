package com.danilodps.kakfaproducer.application.producer;

import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    private final KafkaTemplate <String, Object> kafkaTemplate;

    public KafkaProducer(@Qualifier("producer")KafkaTemplate <String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async("taskExecutor")
    public void send(String topic, UserResponse userResponse){
        log.info("Enviando usuário: {}", userResponse.userId());
        kafkaTemplate.send(topic, userResponse.userId(), userResponse);
    }
}
