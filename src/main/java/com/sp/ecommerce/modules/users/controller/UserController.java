package com.sp.ecommerce.modules.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.service.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import com.sp.ecommerce.shared.utils.*;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import static com.sp.ecommerce.shared.utils.Constants.*;

//@Tag(name ="User", description = "Operations related to users")
@RestController
@RequestMapping("/users")
@NoArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private KafkaProducerUtil<Object> kafkaProducerUtil;

//    @Autowired
//    private RedisUtil<Object> redisUtil;

//    @Autowired
//    @Qualifier("customJSONObjectMapper")
//    ObjectMapper objectMapper;

    @Value("${kafka.topics}")
    private String topic;

    // path variable vs request param
    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userId){
        UserResponseDTO userResponseDTO = this.userService.findUserByUserId(userId);
        return ResponseEntity.ok().body(userResponseDTO);
    }


    // do we need to send json string?
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO requestDTO) throws JsonProcessingException {
        UserResponseDTO userResponseDTO = this.userService.createUser(requestDTO);
        this.kafkaProducerUtil.sendMessage(topic, userResponseDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return ResponseEntity.ok().body(objectMapper.writeValueAsString(userResponseDTO));
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        this.userService.deleteUserById(userId);
        return ResponseEntity.ok().body(USER_DELETE_SUCCESS);
    }
}
