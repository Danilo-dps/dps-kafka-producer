package com.danilodps.kakfaproducer;

import com.danilodps.kakfaproducer.adapter.UserEntity2UserResponse;
import com.danilodps.kakfaproducer.entity.UserEntity;
import com.danilodps.kakfaproducer.producer.KafkaProducer;
import com.danilodps.kakfaproducer.record.request.UserRequest;
import com.danilodps.kakfaproducer.record.response.UserResponse;
import com.danilodps.kakfaproducer.repository.UserEntityRepository;
import com.danilodps.kakfaproducer.service.impl.UserServiceImpl;
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

    UserRequest userRequest;
    UserEntity userEntity;
    UserResponse userResponse;

    @BeforeEach
    void setup(){
        userRequest = UserRequest.builder()
                .name("Danilo")
                .lastName("Pereia")
                .build();

        userEntity = UserEntity.builder()
                .userId(UUID.fromString("871982fe-57a2-4eab-af9f-97e880ee2cbf"))
                .name(userRequest.name())
                .lastName(userRequest.lastName())
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
            userService.create(userRequest);
            mockedStatic.verify(() -> UserEntity2UserResponse.convert(any(UserEntity.class)));
            kafkaProducer.send(userResponse);
        }

        verify(userEntityRepository, atLeastOnce()).saveAndFlush(any(UserEntity.class));
        verify(kafkaProducer, atLeastOnce()).send(any(UserResponse.class));
    }
}