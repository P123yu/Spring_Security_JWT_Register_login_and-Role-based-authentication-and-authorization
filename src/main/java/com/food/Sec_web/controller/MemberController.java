package com.food.Sec_web.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
public class MemberController {

    @GetMapping
    public String getMember(){
        return "member controller";
    }

}
