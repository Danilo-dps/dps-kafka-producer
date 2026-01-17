package com.danilodps.kakfaproducer.service.impl;

import com.danilodps.kakfaproducer.entity.UserEntity;
import com.danilodps.kakfaproducer.producer.KafkaProducer;
import com.danilodps.kakfaproducer.record.request.UserRequest;
import com.danilodps.kakfaproducer.record.response.UserResponse;
import com.danilodps.kakfaproducer.repository.UserEntityRepository;
import com.danilodps.kakfaproducer.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserEntityRepository userEntityRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    public UserResponse create(UserRequest userRequest) {
        log.info("Criando usuário...");

        UserEntity userEntity = UserEntity.builder()
                .name(userRequest.name())
                .lastName(userRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário criado!");
        UserResponse userResponse = UserResponse.builder()
                .userId(userEntity.getUserId().toString())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .build();

        kafkaProducer.send(userResponse);

        return userResponse;
    }
}
