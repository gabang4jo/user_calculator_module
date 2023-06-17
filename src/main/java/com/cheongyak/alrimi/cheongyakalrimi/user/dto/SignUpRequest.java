package com.cheongyak.alrimi.cheongyakalrimi.user.dto;

import lombok.Getter;

@Getter
public class SignUpRequest {
    private String userName;
    private String email;
    private String password;
    private String passwordCheck;
}
