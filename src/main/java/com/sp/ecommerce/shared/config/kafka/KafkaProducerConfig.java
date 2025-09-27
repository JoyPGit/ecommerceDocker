package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.kafka.core.*;

import java.util.*;

/**
 * byte array for serialization, read the bytes for deserialization
 * can Gson be used?
 * <artifactId>spring-kafka</artifactId>
 * @Configuration -> Bean inside
 * Key in topic will channel messages to the same partition
 * On the consumer side, you use a matching Deserializer to convert the byte[] back to
 * Java T
 *
 * Here key is String, and the object is serialized to json
 * ✅ So what's the actual behavior?
 *
 * The object is serialized to JSON using Jackson internally.
 * But the output is a byte[] (UTF-8 encoded JSON string).
 * So, the serialized Kafka message is a JSON string → encoded as bytes (as required by Kafka).
 *
 * Behind the scenes:
 *
 * The JsonSerializer does this under the hood:
 * byte[] jsonBytes = objectMapper.writeValueAsBytes(value);
 *
 * Input: Your Java object (MyEvent, UserDTO, etc.)
 * Output: JSON like {"userId":1,"name":"Alice"} → as byte array
 *
 * template is for sending messages
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${spring.kafka-bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public Map<String, Object> producerFactoryConfig(){
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configMap;
    }

    @Bean
    public ProducerFactory<String, Object> producerFactory(){
        return new DefaultKafkaProducerFactory<>(producerFactoryConfig());
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate (){
        return new KafkaTemplate<>(producerFactory());
    }
}
