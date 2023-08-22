package com.snowhitelog.service;

import com.snowhitelog.domain.Session;
import com.snowhitelog.domain.User;
import com.snowhitelog.exception.AlreadyExistsEmailException;
import com.snowhitelog.exception.InvalidSigninInformation;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Login;
import com.snowhitelog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        User user = User.builder().name(signup.getName()).password(signup.getPassword()).email(signup.getEmail()).build();
        userRepository.save(user);
    }
}
