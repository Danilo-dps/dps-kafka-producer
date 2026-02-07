package com.danilodps.kakfaproducer.domain.model.record.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserUpdateRequest(UUID userId, String name, String lastName) { }
