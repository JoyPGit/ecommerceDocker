package com.sp.ecommerce.shared.config.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sp.ecommerce.shared.config.ObjectMapperJson;
import com.sp.ecommerce.shared.utils.Constants;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.*;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
//    @Qualifier("customObjectMapper")
    private final ObjectMapper objectMapper;

    private final Class<T> targetType; // why final?

    public CustomJsonSerializer(Class<T> targetType) {
        super();
        this.objectMapper = getObjectMapperCustom();
//        this.objectMapperJson =
//                com.sp.ecommerce.shared.config.ObjectMapperJson.getObjectMapperJson();
        this.targetType = targetType;
    }

    private ObjectMapper getObjectMapperCustom(){
        DateTimeFormatter customFormatter =
                DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // register custom serializer and deserializer
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(customFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(customFormatter));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(javaTimeModule);
        objectMapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
        return objectMapper;
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
            return objectMapper.writeValueAsBytes(data);
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
