package com.snowhitelog.controller;

import com.snowhitelog.domain.User;
import com.snowhitelog.exception.InvalidRequest;
import com.snowhitelog.exception.InvalidSigninInformation;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Login;
import com.snowhitelog.response.SessionResponse;
import com.snowhitelog.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> login(@RequestBody Login login) {
        // json id/pw
        log.info(">>>login={}", login);
        // db 조회
        String accessToken = authService.signin(login);
        ResponseCookie cookie = ResponseCookie.from("SESSION", accessToken)
                .domain("localhost")        // todo 서버 환경에 따른 분리 필요
                .path("/")
                .httpOnly(true)
                .secure(false)
                .maxAge(Duration.ofDays(60))
                .sameSite("Strict")
                .build();

        log.info(">>>> cookie ={}", cookie.toString());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }
}
