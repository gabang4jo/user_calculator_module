package com.cheongyak.alrimi.cheongyakalrimi.user.jwt;

import com.cheongyak.alrimi.cheongyakalrimi.user.dto.SingInResponse;
import com.cheongyak.alrimi.cheongyakalrimi.user.dto.UserResponse;
import com.cheongyak.alrimi.cheongyakalrimi.user.dto.RefreshRequest;
import com.cheongyak.alrimi.cheongyakalrimi.user.entity.User;
import com.cheongyak.alrimi.cheongyakalrimi.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private String secretKey = "secretkey";

    // 토큰 유효시간 30분,60분
    private long accessTokenTime = 1 * 60 * 1000L;
    private long refreshTokenTime = 60 * 60 * 1000L;

    private final UserService userService;

    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct //한번만 실행되는 어노테이션
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // JWT Access 토큰 생성
    public String createAccessToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username); // JWT payload 에 저장되는 정보단위, 보통 여기서 user를 식별하는 값을 넣는다.
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + accessTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT Refresh 토큰 생성
    public String createRefreshToken() {
        Date now = new Date();
        return Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token){
        UserDetails userDetails = userService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) throws ExpiredJwtException,Exception {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        return !claims.getBody().getExpiration().before(new Date());
    }

    @Transactional
    public SingInResponse refreshToken(RefreshRequest refreshRequest){
        String username = getUserPk(refreshRequest.getAccessToken());
        User user = userService.getByUserName(username);
        if(user.getRefreshToken().equals(refreshRequest.getRefreshToken())){
            String accessToken = createAccessToken(user.getUsername(), user.getRoles());
            String refreshToken = createRefreshToken();
            user.changeRefreshToken(refreshToken);
            UserResponse userInfo = UserResponse.of(user);
            SingInResponse data = SingInResponse.of(userInfo,accessToken,refreshToken);
            return data;
        }
        else{
            return null;
        }
    }
}
