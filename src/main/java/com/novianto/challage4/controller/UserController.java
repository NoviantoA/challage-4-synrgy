package com.novianto.challage4.controller;

import com.novianto.challage4.dto.UserDto;
import com.novianto.challage4.entity.User;
import com.novianto.challage4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map<String, Object>> saveUser(@RequestBody UserDto request) {
        return new ResponseEntity<Map<String, Object>>(userService.saveUser(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update/{userId}", "/update/{userId}/"})
    public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserDto request, @PathVariable("userId") UUID userId) {
        return new ResponseEntity<Map<String, Object>>(userService.updateUser(userId, request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/delete/{userId}", "/delete/{userId}/"})
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<Map<String, Object>>(userService.deleteUser(userId), HttpStatus.OK);
    }

    @GetMapping(value = {"/get/{userId}", "/get/{userId}/"})
    public ResponseEntity<Map<String, Object>> getById(@PathVariable("userId") UUID userId) {
        return new ResponseEntity<Map<String, Object>>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping(value = {"/allUser", "/allUser/"})
    public ResponseEntity<List<User>> findAllUser(){
        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
}
