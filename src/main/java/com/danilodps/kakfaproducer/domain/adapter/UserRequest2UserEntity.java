package com.danilodps.kakfaproducer.domain.adapter;

import com.danilodps.kakfaproducer.domain.model.entity.UserEntity;
import com.danilodps.kakfaproducer.domain.model.record.request.UserCreateRequest;

import java.time.LocalDateTime;

public class UserRequest2UserEntity {

    private UserRequest2UserEntity(){}

    public static UserEntity convert(UserCreateRequest userCreateRequest){
        return UserEntity.builder()
                .name(userCreateRequest.name())
                .lastName(userCreateRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
