package com.danilodps.kakfaproducer.domain.service;

import com.danilodps.kakfaproducer.domain.model.record.request.UserCreateRequest;
import com.danilodps.kakfaproducer.domain.model.record.request.UserUpdateRequest;
import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse create(UserCreateRequest userCreateRequest);
    UserResponse update(UserUpdateRequest userUpdateRequest);
    String delete(UUID userId);
}
