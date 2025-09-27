package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * return new instance for reusability
 * bean, component?
 *
 * how to add properties to a class after it's instance has been created and it has been
 * autowired?
 * maybe add the properties beforehand return a bean?
 *
 * avoid this if you're implementing a custom deserializer — it defeats the purpose
 * return Deserializer.super.deserialize(topic, headers, data);
 *
 * for method with bytebuffer, convert to byte array
 *
 * where to add this ???
 * deserializer.addTrustedPackages("com.sp.ecommerce.modules.users.dto.response");
 * deserializer.setUseTypeMapperForKey(true);
 *
 * With your custom deserializer (no addTrustedPackages needed):
 * @param <T>
 *
 */
@Component
public class CustomJsonDeserializer<T> implements Deserializer<T> {

    ObjectMapper objectMapperJson;
    private final Class<T> targetType; // why final?

    public CustomJsonDeserializer(Class<T> targetType) {
        super();
        this.objectMapperJson = new ObjectMapper();
        this.targetType = targetType;

    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public T deserialize(String s, byte[]data) {
        if (data == null) return null;
        try {
            return this.objectMapperJson.readValue(data, targetType);
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize Kafka message", e);
        }
    }

    /**
     * hwo to integrate headers
     *
     * Header typeHeader = headers.lastHeader("type");
     * if (typeHeader != null) {
     *     String typeName = new String(typeHeader.value(), StandardCharsets.UTF_8);
     *     Class<?> dynamicType = Class.forName(typeName);
     *     return objectMapper.readValue(data, dynamicType);
     * }
     *
     * @param topic
     * @param headers
     * @param data
     * @return
     */
    @Override
    public T deserialize(String topic, Headers headers, byte[] data) {
        if (data == null) return null;
        try {
            Header typeHeader = headers.lastHeader("type");
            if (typeHeader != null) {
                String typeName = new String(typeHeader.value(), StandardCharsets.UTF_8);
                Class<?> dynamicType = Class.forName(typeName);
                return (T) objectMapperJson.readValue(data, dynamicType);
            }
        } catch (Exception e) {
            throw new SerializationException("Failed to deserialize Kafka message", e);
        }
        return null;
    }

    @Override
    public T deserialize(String topic, Headers headers, ByteBuffer data) {
        if (data == null || !data.hasRemaining()) {
            return null;
        }
        try{
            byte[] bytes = new byte[data.remaining()];
            data.get(bytes); // Copy ByteBuffer contents into byte array
            return objectMapperJson.readValue(bytes, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
    }
}
