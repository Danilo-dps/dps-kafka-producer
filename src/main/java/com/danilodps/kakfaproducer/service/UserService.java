package com.danilodps.kakfaproducer.service;

import com.danilodps.kakfaproducer.record.request.UserRequest;
import com.danilodps.kakfaproducer.record.response.UserResponse;

public interface UserService {

    UserResponse create(UserRequest userRequest);
}
