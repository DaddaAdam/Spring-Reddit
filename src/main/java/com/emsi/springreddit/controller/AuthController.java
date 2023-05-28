package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.GenericResponse;
import com.emsi.springreddit.dto.request.AuthenticationRequest;
import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.dto.response.AuthenticationResponse;
import com.emsi.springreddit.dto.response.RegisterResponse;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*")
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
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponse(
                            HttpStatus.BAD_REQUEST.value(),
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

    @GetMapping("/me")
    public ResponseEntity<GenericResponse> me() {
        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user == null) throw new NoSuchElementException("User not auth");
            return ResponseEntity.status(200).body(new GenericResponse(200, "OK", null, user));
        }
        catch (NoSuchElementException exception){
            return ResponseEntity.status(401).body(new GenericResponse(401, "Not authorized", "Not authorized", null));
        }
        catch (Exception exception){
            return ResponseEntity.status(500).body(new GenericResponse(500, "Internal Server Error", exception.getMessage(), null));
        }

    }
}
