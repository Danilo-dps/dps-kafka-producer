package com.danilodps.kakfaproducer.domain.service.impl;

import com.danilodps.kakfaproducer.application.exceptions.NotFoundException;
import com.danilodps.kakfaproducer.application.producer.KafkaProducer;
import com.danilodps.kakfaproducer.domain.adapter.UserEntity2UserResponse;
import com.danilodps.kakfaproducer.domain.adapter.UserRequest2UserEntity;
import com.danilodps.kakfaproducer.domain.model.entity.UserEntity;
import com.danilodps.kakfaproducer.domain.model.record.request.UserCreateRequest;
import com.danilodps.kakfaproducer.domain.model.record.request.UserUpdateRequest;
import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;
import com.danilodps.kakfaproducer.domain.repository.UserEntityRepository;
import com.danilodps.kakfaproducer.domain.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${spring.kafka.producer.topic.kafka-created}")
    private String producerCreated;
    @Value("${spring.kafka.producer.topic.kafka-updated}")
    private String producerUpdated;
    @Value("${spring.kafka.producer.topic.kafka-deleted}")
    private String producerDeleted;

    private final UserEntityRepository userEntityRepository;
    private final KafkaProducer kafkaProducer;

    @Override
    @Transactional
    public UserResponse create(UserCreateRequest userCreateRequest) {
        log.info("Criando usuário...");
        UserEntity userEntity = UserRequest2UserEntity.convert(userCreateRequest);

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário criado com userId {}", userEntity.getUserId());

        UserResponse userResponse = UserEntity2UserResponse.convert(userEntity);
        kafkaProducer.send(producerCreated, userResponse);
        log.info("Usuário criado enviado para kafka com userId {}", userEntity.getUserId());
        return userResponse;
    }

    @Override
    public UserResponse update(UserUpdateRequest userUpdateRequest) {
        log.info("Buscando usuário para atualizar");
        UserEntity userEntity = userEntityRepository.findByUserId(userUpdateRequest.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        userEntity.setName(userUpdateRequest.name());
        userEntity.setLastName(userUpdateRequest.lastName());
        userEntity.setUpdatedAt(LocalDateTime.now());

        userEntityRepository.saveAndFlush(userEntity);
        log.info("Usuário atualizado com userId {}", userEntity.getUserId());
        UserResponse userResponse = UserEntity2UserResponse.convert(userEntity);
        kafkaProducer.send(producerUpdated, userResponse);
        log.info("Usuário atualizado enviado para kafka com userId {}", userEntity.getUserId());
        return userResponse;
    }

    @Override
    public String delete(UUID userId) {
        UserEntity userEntity = userEntityRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        UserResponse userResponse = UserEntity2UserResponse.convert(userEntity);
        kafkaProducer.send(producerDeleted, userResponse);
        userEntityRepository.delete(userEntity);
        log.info("Usuário deletado enviado para kafka com userId {}", userResponse.userId());
        return "Usuário excluido";
    }
}
