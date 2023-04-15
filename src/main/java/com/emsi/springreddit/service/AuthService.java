package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.entities.VerificationToken;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.repository.UserRepository;
import com.emsi.springreddit.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()){
            throw new UserAlreadyExistsException("Registration failed: username is already taken");
        }
        if(userRepository.findByUsername(registerRequest.getEmail()).isPresent()){
            throw new UserAlreadyExistsException("Registration failed: email is already taken");
        }

        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setToken(token);

        verificationTokenRepository.save(verificationToken);

        return token;
    }


}
