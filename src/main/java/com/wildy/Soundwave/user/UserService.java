package com.wildy.Soundwave.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(String email, String password, Role role) {
        User user = User.builder()
                .email(email)
                .password(password)
                .role(role)
                .build();

        return userRepository.save(user);
    }
}

