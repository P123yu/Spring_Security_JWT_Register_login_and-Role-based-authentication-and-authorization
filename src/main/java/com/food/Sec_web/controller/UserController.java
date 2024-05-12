package com.food.Sec_web.controller;


import com.food.Sec_web.jwt.Token;
import com.food.Sec_web.model.Login;
import com.food.Sec_web.model.User;
import com.food.Sec_web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public ResponseEntity<Token>register(@RequestBody User user){
        Token token=userService.register(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<Token>login(@RequestBody Login login){
        Token token=userService.login(login);
        return ResponseEntity.ok(token);
    }




}
