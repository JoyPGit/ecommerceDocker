package com.sp.ecommerce.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.*;

/**
 * new object mapper for reusability
 * should bean methods be only public?
 *
 * Spring uses reflection to call private methods.
 * ✅ Best practice: Use public or package-private unless you have a specific reason.
 *
 */
@Configuration
public class ObjectMapperJson {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
