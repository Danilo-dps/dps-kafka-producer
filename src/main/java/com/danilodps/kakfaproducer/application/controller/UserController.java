package com.danilodps.kakfaproducer.application.controller;

import com.danilodps.kakfaproducer.domain.model.record.request.UserCreateRequest;
import com.danilodps.kakfaproducer.domain.model.record.request.UserUpdateRequest;
import com.danilodps.kakfaproducer.domain.model.record.response.UserResponse;
import com.danilodps.kakfaproducer.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest userCreateRequest){
        UserResponse userCreated = userService.create(userCreateRequest);
        return ResponseEntity.ok(userCreated);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> update(@RequestBody UserUpdateRequest userUpdateRequest){
        UserResponse userCreated = userService.update(userUpdateRequest);
        return ResponseEntity.ok(userCreated);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam UUID userId){
        return ResponseEntity.ok(userService.delete(userId));
    }
}
