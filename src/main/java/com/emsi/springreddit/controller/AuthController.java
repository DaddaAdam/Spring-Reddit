package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.dto.response.RegisterResponse;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                    .status(HttpStatus.CONFLICT)
                    .body(new RegisterResponse(
                            HttpStatus.CONFLICT.value(),
                            "User Already Exists",
                            exception.getMessage())
                    );
        }
    }
}
