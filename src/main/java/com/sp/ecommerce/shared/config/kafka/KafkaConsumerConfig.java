package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.kafka.config.*;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.*;

/**
 * deserialize to any generic class? any json?
 * Spring kafka deserializer used to work out of the box, but is deprecated
 * So implement custom Deserializer
 */
@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka-bootstrap-servers}")
    private String bootStrapServers;

    @Value("${kafka.group-id}")
    private String groupId;


    @Autowired
    private CustomJsonDeserializer customJsonDeserializer;

    @Bean
    public Map<String, Object> consumerFactoryConfig(){
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);

        return configMap;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerFactoryConfig(),
                new StringDeserializer(), customJsonDeserializer);
    }


    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,
            String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
