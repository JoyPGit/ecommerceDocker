package com.sp.ecommerce.shared.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * custom bean kafkaListenerContainerFactory
 *
 * @param <T>
 */
@Slf4j
public class KafkaConsumerUtil<T> {

    @Value("${kafka.topics}")
    private String topic;

    @Value("${kafka.group-id}")
    private String groupId;

    @KafkaListener(topics = "topic",
    groupId = "groupId",
    containerGroup = "kafkaListenerContainerFactory")
    public void consumeMessage(ConsumerRecord<String, Object> message){
        log.info("Received message {} : ", message);
    }
}

