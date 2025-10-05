package com.sp.ecommerce.modules.users.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.service.UserService;
import com.sp.ecommerce.shared.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.cache.annotation.*;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "Get a user by id")
    @ApiResponses(
            value = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                            description = "User found successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404",
                            description = "User not found"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500",
                            description = "Internal server error")
            }
    )
    // path variable vs request param
    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userId){
        UserResponseDTO userResponseDTO = this.userService.findUserByUserId(userId);
        return ResponseEntity.ok().body(userResponseDTO);
    }


    @Operation(summary = "Create a new user")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",
                            description = "User created successfully"),
                    @ApiResponse(responseCode = "400", description = "Bad Request"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "403", description = "Forbidden"),
                    @ApiResponse(responseCode = "404", description = "Not Found"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
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
