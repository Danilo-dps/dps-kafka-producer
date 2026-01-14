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
    private static final String USER_V1_CREATED = "user-create-v1";

    public void send(UserResponse userResponse){
        log.info("Usuário v1 criado com o UUID: {}", userResponse.userId());
        kafkaTemplate.send(USER_V1_CREATED, userResponse.userId().toString(), userResponse);
    }
}
