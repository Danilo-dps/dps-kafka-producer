package com.danilodps.kakfaproducer.controller;

import com.danilodps.kakfaproducer.record.request.UserRequest;
import com.danilodps.kakfaproducer.record.response.UserResponse;
import com.danilodps.kakfaproducer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userRequest){
        UserResponse userCreated = userService.create(userRequest);
        return ResponseEntity.ok(userCreated);
    }
}
