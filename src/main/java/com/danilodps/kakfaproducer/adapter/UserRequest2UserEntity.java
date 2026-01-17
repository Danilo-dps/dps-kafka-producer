package com.danilodps.kakfaproducer.adapter;

import com.danilodps.kakfaproducer.entity.UserEntity;
import com.danilodps.kakfaproducer.record.request.UserRequest;

import java.time.LocalDateTime;

public class UserRequest2UserEntity {

    private UserRequest2UserEntity(){}

    public static UserEntity convert(UserRequest userRequest){
        return UserEntity.builder()
                .name(userRequest.name())
                .lastName(userRequest.lastName())
                .createdAt(LocalDateTime.now())
                .build();
    }

}
