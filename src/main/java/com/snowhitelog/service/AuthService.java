package com.snowhitelog.service;

import com.snowhitelog.crypto.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmail(login.getEmail()).orElseThrow(InvalidSigninInformation::new);

        var matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        User user = User.builder().name(signup.getName()).password(encryptedPassword).email(signup.getEmail()).build();
        userRepository.save(user);
    }
}
