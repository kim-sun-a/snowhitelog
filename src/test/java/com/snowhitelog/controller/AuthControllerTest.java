package com.snowhitelog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowhitelog.domain.User;
import com.snowhitelog.repository.SessionRepository;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Login;
import com.snowhitelog.request.PostCreate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {  // 테스트 실행되기 전에 수행해주는 라이브러리
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("로그인 성공")
    void test1() throws Exception {
        //given
        userRepository.save(User.builder().name("서나").email("suna@gmail.com").password("1234").build());

        Login login = Login.builder().email("suna@gmail.com").password("1234").build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))      //application/json
                .andExpect(status().isOk())
                .andDo(print());
    }


    @Test
    @Transactional
    @DisplayName("로그인 성공 후 세션 1개 생성")
    void test2() throws Exception {
        //given
        User user = userRepository.save(User.builder().name("서나").email("suna@gmail.com").password("1234").build());

        Login login = Login.builder().email("suna@gmail.com").password("1234").build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))      //application/json
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1L, user.getSessions().size());

    }


    @Test
    @DisplayName("로그인 성공 후 세션 응답")
    void test3() throws Exception {
        //given
        User user = userRepository.save(User.builder().name("서나").email("suna@gmail.com").password("1234").build());

        Login login = Login.builder().email("suna@gmail.com").password("1234").build();

        String json = objectMapper.writeValueAsString(login);

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(json))      //application/json
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", Matchers.notNullValue()))
                .andDo(print());


    }
}