package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.request.AuthenticationRequest;
import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.dto.response.AuthenticationResponse;
import com.emsi.springreddit.dto.response.RegisterResponse;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest) {
        try{
            authService.signup(registerRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new RegisterResponse(
                            HttpStatus.OK.value(),
                            "User Registration Successful",
                            null)
                    );

        }catch(UserAlreadyExistsException exception){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new RegisterResponse(
                            HttpStatus.CREATED.value(),
                            "User Already Exists",
                            exception.getMessage())
                    );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest authRequest) {
        try{
            AuthenticationResponse authResponse = authService.login(authRequest);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponse);

        }catch(AuthenticationException exception){
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthenticationResponse(
                            HttpStatus.UNAUTHORIZED.value(),
                            "Authentication failed",
                            null,
                            exception.getMessage())
                    );
        }
    }
}
