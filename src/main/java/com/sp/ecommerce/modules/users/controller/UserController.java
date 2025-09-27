package com.sp.ecommerce.modules.users.controller;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.service.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import com.sp.ecommerce.shared.utils.KafkaProducerUtil;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

//@Tag(name ="User", description = "Operations related to users")
@RestController
@RequestMapping("/users")
@NoArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    KafkaProducerUtil<Object> kafkaProducerUtil;

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
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO requestDTO){
        UserResponseDTO userResponseDTO = this.userService.createUser(requestDTO);
        this.kafkaProducerUtil.sendMessage(topic, userResponseDTO);
        return ResponseEntity.ok().body(userResponseDTO);
    }
}
