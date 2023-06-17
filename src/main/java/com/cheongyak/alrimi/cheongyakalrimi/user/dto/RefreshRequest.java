package com.cheongyak.alrimi.cheongyakalrimi.user.dto;

import lombok.Getter;

@Getter
public class RefreshRequest {
    private String accessToken;

    private String refreshToken;

}
