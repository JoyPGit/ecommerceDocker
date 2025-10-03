package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sp.ecommerce.shared.utils.*;
import com.sp.ecommerce.shared.utils.mapper.UserPOJOMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.*;
import org.apache.kafka.common.serialization.Deserializer;
import org.springframework.beans.factory.annotation.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
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
 *
 * (T) for converting from one POJO to another, mapstruct?
 * @param <T>
 *
 */
//@Component
public class CustomJsonDeserializer<T> implements Deserializer<T> {

    @Autowired
    private UserPOJOMapper userPOJOMapper;

    // @Autowired
    // @Qualifier("customJSONObjectMapper")
    private ObjectMapper objectMapper;
     private final Class<T> targetType; // why final?

    public CustomJsonDeserializer(Class<T> targetType) {
        super();
//        this.objectMapperJson =
//                com.sp.ecommerce.shared.utils.mapper.ObjectMapperJson.getObjectMapperJson();
        this.targetType = targetType;
        this.objectMapper = getObjectMapperCustom();
    }

    private ObjectMapper getObjectMapperCustom(){
        DateTimeFormatter customFormatter =
                DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public T deserialize(String s, byte[]data) {
        if (data == null) return null;
        try {
            return this.objectMapper.readValue(data, targetType);
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
                return (T) objectMapper.readValue(data, dynamicType);
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
            return objectMapper.readValue(bytes, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
    }
}
