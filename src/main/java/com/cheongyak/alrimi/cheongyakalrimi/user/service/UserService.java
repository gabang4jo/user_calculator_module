package com.cheongyak.alrimi.cheongyakalrimi.user.service;

import java.util.Arrays;

import com.cheongyak.alrimi.cheongyakalrimi.user.dto.SignInRequest;
import com.cheongyak.alrimi.cheongyakalrimi.user.dto.SignUpRequest;
import com.cheongyak.alrimi.cheongyakalrimi.user.dto.UserResponse;
import com.cheongyak.alrimi.cheongyakalrimi.user.entity.User;
import com.cheongyak.alrimi.cheongyakalrimi.user.exception.InvalidArgumentException;
import com.cheongyak.alrimi.cheongyakalrimi.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private boolean isValidUserName(String userName) {
        if (userRepository.existsUserByUserName(userName)) {
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String passwordCheck) {
        if (password.equals(passwordCheck)) {
            return true;
        }
        return false;
    }

    public UserResponse signUp(SignUpRequest request) {
        if (!isValidUserName(request.getUserName())) {
            throw new InvalidArgumentException("이미 존재하는 이름입니다.");
        }
        if (!isValidPassword(request.getPassword(), request.getPasswordCheck())) {
            throw new InvalidArgumentException("비밀번호가 일치하지 않습니다.");
        }
        User newUser = User
                .builder()
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Arrays.asList("ROLE_USER"))
                .build();
        return UserResponse.of(userRepository.save(newUser));
    }

    public UserResponse signIn(SignInRequest request) {
        User user = userRepository.findUserByUserName(request.getUserName())
                .orElseThrow(() -> new InvalidArgumentException("존재하지 않는 이름입니다."));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return UserResponse.of(user);
    }

    public void refreshToken(UserResponse userResponse, String refreshToken) {
        User user = getByUserName(userResponse.getUsername());
        user.changeRefreshToken(refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }

    public User getByUserName(String name) {
        return userRepository.findByUserName(name).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
    }
}
