package com.example.friendling.controllers;

import com.example.friendling.models.dtos.UserDto;
import com.example.friendling.services.CustomUserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserController {

    private final CustomUserDetailsService customUserDetailsService;

    UserController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @GetMapping("/list")
    public List<UserDto> getUsers() {
        return customUserDetailsService.getUserList();
    }
    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable long id) {
        return customUserDetailsService.getUser(id);
    }
    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) {
        return customUserDetailsService.createUser(user);
    }
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable long id, @RequestBody UserDto user) {
        return customUserDetailsService.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        customUserDetailsService.deleteUser(id);
    }
}