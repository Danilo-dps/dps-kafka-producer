package com.danilodps.kakfaproducer;

import com.danilodps.kakfaproducer.domain.adapter.UserEntity2UserResponse;
import com.danilodps.kakfaproducer.domain.model.entity.UserEntity;
import com.danilodps.kakfaproducer.application.producer.KafkaProducer;
import com.danilodps.kakfaproducer.domain.model.record.request.UserCreateRequest;
import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;
import com.danilodps.kakfaproducer.domain.repository.UserEntityRepository;
import com.danilodps.kakfaproducer.domain.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserEntityRepository userEntityRepository;

    @Mock
    KafkaProducer kafkaProducer;

    @InjectMocks
    UserServiceImpl userService;

    UserCreateRequest userCreateRequest;
    UserEntity userEntity;
    UserResponse userResponse;

    @BeforeEach
    void setup(){
        userCreateRequest = UserCreateRequest.builder()
                .name("Danilo")
                .lastName("Pereira")
                .build();

        userEntity = UserEntity.builder()
                .userId(UUID.fromString("871982fe-57a2-4eab-af9f-97e880ee2cbf"))
                .name(userCreateRequest.name())
                .lastName(userCreateRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();

        userResponse = UserResponse.builder()
                .userId(userEntity.getUserId().toString())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .build();
    }

    @Test
    void testCreateAUser() {
        when(userEntityRepository.saveAndFlush(any(UserEntity.class))).thenReturn(userEntity);

        try (MockedStatic<UserEntity2UserResponse> mockedStatic = mockStatic(UserEntity2UserResponse.class)) {
            mockedStatic.when(() -> UserEntity2UserResponse.convert(any(UserEntity.class)))
                    .thenReturn(userResponse);
            userService.create(userCreateRequest);
            mockedStatic.verify(() -> UserEntity2UserResponse.convert(any(UserEntity.class)));
            kafkaProducer.send("test-topic",userResponse);
        }

        verify(userEntityRepository, atLeastOnce()).saveAndFlush(any(UserEntity.class));
        verify(kafkaProducer, atLeastOnce()).send(anyString(), any(UserResponse.class));
        Assertions.assertEquals("Danilo", userEntity.getName());
        Assertions.assertEquals("Pereira", userEntity.getLastName());
        Assertions.assertNotNull(userEntity.getCreatedAt());
    }


    @Test
    void testUpdateAUser() {
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setLastName("Pereira Update");
        userEntityRepository.saveAndFlush(any(UserEntity.class));
        kafkaProducer.send(anyString(), any(UserResponse.class));

        Assertions.assertEquals("Danilo", userEntity.getName());
        Assertions.assertEquals("Pereira Update", userEntity.getLastName());
        Assertions.assertNotNull(userEntity.getCreatedAt());
        verify(kafkaProducer, atLeastOnce()).send(anyString(), any());
        verify(userEntityRepository, atLeastOnce()).saveAndFlush(any());
    }

    @Test
    void testDeleteAUser() {

        kafkaProducer.send(anyString(), any(UserResponse.class));
        userEntityRepository.delete(userEntity);

        verify(userEntityRepository, atLeastOnce()).delete(any());
        verify(kafkaProducer, atLeastOnce()).send(anyString(), any());
    }
}