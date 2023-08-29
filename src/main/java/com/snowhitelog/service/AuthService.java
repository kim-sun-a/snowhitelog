package com.snowhitelog.service;

import com.snowhitelog.crypto.PasswordEncoder;
import com.snowhitelog.domain.User;
import com.snowhitelog.exception.AlreadyExistsEmailException;
import com.snowhitelog.repository.UserRepository;
import com.snowhitelog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        PasswordEncoder encoder = new PasswordEncoder();
        String encryptPassword = encoder.encrypt(signup.getPassword());

        User user = User.builder().name(signup.getName()).password(encryptPassword).email(signup.getEmail()).build();
        userRepository.save(user);
    }
}
