package com.cheongyak.alrimi.cheongyakalrimi.user.dto;

import com.cheongyak.alrimi.cheongyakalrimi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SingInResponse {
    private UserResponse user;

    private String accessToken;

    private String refreshToken;

    public static SingInResponse of(UserResponse user,String accessToken,String refreshToken){
        return new SingInResponse(user,accessToken,refreshToken);
    }
}
