package com.danilodps.kakfaproducer.record;

import lombok.Builder;

@Builder
public record UserRecord(String name, String lastName) { }
