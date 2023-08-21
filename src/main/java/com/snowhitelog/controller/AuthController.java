package com.snowhitelog.controller;

import com.snowhitelog.request.Login;
import com.snowhitelog.response.SessionResponse;
import com.snowhitelog.service.AuthService;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private static final String KEY = "OSWfVh9o/LuU6b2u2/9A0KvBf8JKipvhakdYHt1xA9A=";

    @PostMapping("/auth/login")
    public SessionResponse login(@RequestBody Login login) {
        // json id/pw
        log.info(">>>login={}", login);
        // db 조회
        Long userId = authService.signin(login);
        SecretKey key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));
        String jws = Jwts.builder().setSubject(String.valueOf(userId)).signWith(key).compact();

        return new SessionResponse(jws);
    }
}
