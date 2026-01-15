package com.danilodps.kakfaproducer.producer;

import com.danilodps.kakfaproducer.record.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate <String, Object> kafkaTemplate;
    private static final String TOPIC_USER_V1 = "topic.user.v1";

    public void send(UserResponse userResponse){
        log.info("Usuário v1 criado com o userId: {}", userResponse.userId());
        kafkaTemplate.send(TOPIC_USER_V1, userResponse.userId().toString(), userResponse);
    }
}
