package com.snowhitelog.service;

import com.snowhitelog.domain.Session;
import com.snowhitelog.domain.User;
import com.snowhitelog.exception.InvalidSigninInformation;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();
        return user.getId();
    }
}
