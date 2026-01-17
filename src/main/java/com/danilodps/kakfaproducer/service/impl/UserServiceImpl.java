package com.danilodps.kakfaproducer.service.impl;

import com.danilodps.kakfaproducer.adapter.UserEntity2UserResponse;
import com.danilodps.kakfaproducer.adapter.UserRequest2UserEntity;
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
        UserEntity userEntity = UserRequest2UserEntity.convert(userRequest);

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário criado com userId {}", userEntity.getUserId());

        UserResponse userResponse = UserEntity2UserResponse.convert(userEntity);
        kafkaProducer.send(userResponse);
        log.info("Usuário enviado via kafka com userId {}", userEntity.getUserId());
        return userResponse;
    }
}
