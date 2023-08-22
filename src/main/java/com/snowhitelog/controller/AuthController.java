package com.snowhitelog.controller;

import com.snowhitelog.config.data.AppConfig;
import com.snowhitelog.request.Login;
import com.snowhitelog.response.SessionResponse;
import com.snowhitelog.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AppConfig appConfig;

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        // json id/pw
        log.info(">>>login={}", login);
        // db 조회
        Long userId = authService.signin(login);
        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());
        String jws = Jwts.builder().setSubject(String.valueOf(userId)).signWith(key).setIssuedAt(new Date()).compact();

        return new SessionResponse(jws);
    }
}
