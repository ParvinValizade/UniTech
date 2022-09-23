package com.unitech.unitech.controller;

import com.unitech.unitech.dto.UserDto;
import com.unitech.unitech.dto.request.CreateUserRequest;
import com.unitech.unitech.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest request){
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

}
