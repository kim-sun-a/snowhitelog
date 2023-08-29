package com.snowhitelog.service;

import com.snowhitelog.domain.User;
import com.snowhitelog.exception.AlreadyExistsEmailException;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입 성공")
    void test1() {
        // given
        Signup signup = Signup.builder()
                .name("suna")
                .password("1234")
                .email("suna@gmail.com").build();

        // when
        authService.signup(signup);

        //then
        assertEquals(1, userRepository.count());
        User user = userRepository.findAll().iterator().next();
        assertEquals("suna@gmail.com", user.getEmail());
        assertNotNull(user.getPassword());
        assertEquals("1234", user.getPassword());
        assertEquals("suna", user.getName());

    }


    @Test
    @DisplayName("회원가입시 중복된 이메일")
    void test2() {
        // given
        User user = User.builder().email("suna@gmail.com").password("1234").name("서나").build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .name("suna")
                .password("1234")
                .email("suna@gmail.com").build();

        //expected
        assertThrows(AlreadyExistsEmailException.class, () -> authService.signup(signup));
    }

}