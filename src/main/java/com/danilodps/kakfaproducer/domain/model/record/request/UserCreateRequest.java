package com.danilodps.kakfaproducer.domain.model.record.request;

import lombok.Builder;

@Builder
public record UserCreateRequest(String name, String lastName) { }
