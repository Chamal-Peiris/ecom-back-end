package com.chamal.controller;

import com.chamal.model.UserDao;
import com.chamal.service.JwtUserDetailsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private JwtUserDetailsService userService;

    public UserController(JwtUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable String userId){
        return ResponseEntity.ok(userService.getUser(Long.parseLong(userId)));
    }

    @GetMapping("/get-current-user")
    public ResponseEntity getCurrentUser(UserDao userDao){
        return ResponseEntity.ok(userDao);

    }

    @GetMapping()
    public ResponseEntity getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
