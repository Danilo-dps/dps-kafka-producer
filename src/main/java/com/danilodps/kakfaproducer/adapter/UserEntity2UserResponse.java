package com.danilodps.kakfaproducer.adapter;

import com.danilodps.kakfaproducer.entity.UserEntity;
import com.danilodps.kakfaproducer.record.response.UserResponse;

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
