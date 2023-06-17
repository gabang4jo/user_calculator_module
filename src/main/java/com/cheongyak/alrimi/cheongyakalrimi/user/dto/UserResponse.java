package com.cheongyak.alrimi.cheongyakalrimi.user.dto;

import com.cheongyak.alrimi.cheongyakalrimi.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserResponse {
    private String username;

    private List<String> roles;

    public static UserResponse of(User user){
        return new UserResponse(user.getUsername(), user.getRoles());
    }
}
