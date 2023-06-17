package com.cheongyak.alrimi.cheongyakalrimi.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthTestController {
    @GetMapping("/test")
    public String test(Principal user){
        return "test success";
    }
}
