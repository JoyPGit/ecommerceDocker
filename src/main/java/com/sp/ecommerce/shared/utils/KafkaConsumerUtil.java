package com.sp.ecommerce.shared.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * custom bean kafkaListenerContainerFactory
 *
 * @param <T>
 */
@Service
@Slf4j
public class KafkaConsumerUtil<T> {

    @KafkaListener(
            topics = "#{'${kafka.topics}'.split(',')}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumeMessage(ConsumerRecord<String, Object> message){
        log.info("Received message {} : ", message);
    }
}

