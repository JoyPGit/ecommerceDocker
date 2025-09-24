package com.sp.ecommerce.modules.users.controller;

import com.sp.ecommerce.modules.users.dto.request.UserRequestDTO;
import com.sp.ecommerce.modules.users.dto.response.UserResponseDTO;
import com.sp.ecommerce.modules.users.service.UserService;
//import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Tag(name ="User", description = "Operations related to users")
@RestController
@RequestMapping("/users")
@NoArgsConstructor
public class UserController {

    @Autowired
    UserService userService;

    // path variable vs request param
    @GetMapping("/id/{userId}")
    public ResponseEntity<?> getUserByUserId(@PathVariable String userId){
        UserResponseDTO userResponseDTO = this.userService.findUserByUserId(userId);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    // @Valid annotation
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO requestDTO){
        UserResponseDTO userResponseDTO = this.userService.createUser(requestDTO);
        return ResponseEntity.ok().body(userResponseDTO);
    }
}
