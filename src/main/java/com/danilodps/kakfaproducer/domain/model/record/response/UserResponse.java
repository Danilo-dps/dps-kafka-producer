package com.danilodps.kakfaproducer.domain.model.record.response;

import lombok.Builder;

@Builder
public record UserResponse(String userId, String name, String lastName) { }
