package com.danilodps.kakfaproducer.domain.adapter;

import com.danilodps.kakfaproducer.domain.model.entity.UserEntity;
import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;

public class UserEntity2UserResponse {

    private UserEntity2UserResponse(){}

    public static UserResponse convert(UserEntity userEntity){
        return UserResponse.builder()
                .userId(userEntity.getUserId().toString())
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .build();
    }

}
