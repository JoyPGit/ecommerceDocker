package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.*;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 *
 * if confused about the params, check the definition in interface using
 * download sources
 *
 * @param <T>
 *
 */
//@Component
public class CustomJsonSerializer<T> implements Serializer<T> {

    @Autowired
    @Qualifier("customObjectMapper")
    ObjectMapper objectMapperJson;
    private final Class<T> targetType; // why final?

    public CustomJsonSerializer(Class<T> targetType) {
        super();
        this.objectMapperJson = new ObjectMapper();
        this.targetType = targetType;
    }


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        // No config needed here
    }

    /**
     * topic is specified while publishing, this method is for
     * converting String to byte array
     * Do I need JSON String ?????
     *
     * @param topic
     * @param data
     * @return
     */
    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) {
            return null;
        }
        try {
            return objectMapperJson.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new SerializationException("Error serializing JSON message", e);
        }
    }

    /**
     * reuse same method as topic and headers are not necessary ???
     *
     *
     * @param topic
     * @param headers
     * @param data
     * @return
     */
    @Override
    public byte[] serialize(String topic, Headers headers, T data) {
        return serialize(topic, data);
    }

    @Override
    public void close() {
    }
}
