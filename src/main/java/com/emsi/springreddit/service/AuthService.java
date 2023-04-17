package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.AuthenticationRequest;
import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.dto.response.AuthenticationResponse;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.entities.VerificationToken;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.repository.UserRepository;
import com.emsi.springreddit.repository.VerificationTokenRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void signup(RegisterRequest registerRequest) throws UserAlreadyExistsException {

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
        //For testing purposes, the default value of Enabled has been set to true
        user.setEnabled(true); //todo: setEnabled should be turned to false once account verification has been implemented

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


    public AuthenticationResponse login(AuthenticationRequest authRequest) throws AuthenticationException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist"));

        var jwtToken =  jwtService.generateToken(user);

        return new AuthenticationResponse(
                200,
                "Authentication successful",
                jwtToken,
                null
        );
    }
}
