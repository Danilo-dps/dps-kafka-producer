package com.danilodps.kakfaproducer.producer;

import com.danilodps.kakfaproducer.record.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaProducer {

    private static final String TOPIC_USER_V1 = "topic.user.v1";
    private final KafkaTemplate <String, Object> kafkaTemplate;

    public KafkaProducer(@Qualifier("producer")KafkaTemplate <String, Object> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserResponse userResponse){
        log.info("Enviando usuário: {}", userResponse.userId());
        kafkaTemplate.send(TOPIC_USER_V1, userResponse.userId(), userResponse);
    }
}
