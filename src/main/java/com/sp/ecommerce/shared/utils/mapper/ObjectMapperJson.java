package com.sp.ecommerce.shared.utils.mapper;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.sp.ecommerce.shared.utils.Constants;
import org.springframework.context.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * new object mapper for reusability
 * should bean methods be only public?
 *
 * Spring uses reflection to call private methods.
 * ✅ Best practice: Use public or package-private unless you have a specific reason.
 *
 */
@Configuration
public class ObjectMapperJson extends ObjectMapper{

    private ObjectMapper mapper;

//    ObjectMapperJson(){}

//    @Bean("customObjectMapper")
    public ObjectMapper getObjectMapperJson(){
        this.mapper = new ObjectMapper();
        setDateTimeAdapter();
        return this.mapper;
    }

//    @Bean
//    public Jackson2ObjectMapperBuilderCustomizer customizer() {
//        return builder -> {
//            builder.modules(new JavaTimeModule());
//            builder.simpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        };
//    }

    @Primary
    @Bean("customJSONObjectMapper")
    public ObjectMapper getObjectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

    public void setDateTimeAdapter() {
        DateTimeFormatter customFormatter =
                DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // register custom serializer and deserializer
        javaTimeModule.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(customFormatter));
        javaTimeModule.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(customFormatter));

        this.mapper.registerModule(javaTimeModule);
        this.mapper.setDateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT));
    }
}
