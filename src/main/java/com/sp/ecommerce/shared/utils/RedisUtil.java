package com.sp.ecommerce.shared.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.*;

@Service
public class RedisUtil<T> {
    @Autowired
    private RedisTemplate<String, T> redisTemplate;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    @Qualifier("customJSONObjectMapper")
    private ObjectMapper objectMapper;

    public void set(String key, T value){
        redisTemplate.opsForValue().set(key, value);
    }

    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // cast or convert message to string while setting key
    public void getCacheValues(T message) throws JsonProcessingException {
        UserResponseDTO data = objectMapper.readValue(message.toString(),
                UserResponseDTO.class);
    }
}
