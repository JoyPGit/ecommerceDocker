package com.sp.ecommerce.shared.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerUtil<T> {

    @Autowired
    private KafkaTemplate<String, T> kafkaTemplate;

    public void sendMessage(String topic, T message) {
        kafkaTemplate.send(topic, message);
    }

    public void sendMessage(String topic, String key, T message) {
        kafkaTemplate.send(topic, key, message);
    }
}
