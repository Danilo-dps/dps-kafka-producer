package com.danilodps.kakfaproducer.service;

import com.danilodps.kakfaproducer.record.UserRecord;

public interface KafkaProducerService {

    String send(UserRecord userRecord);
}
