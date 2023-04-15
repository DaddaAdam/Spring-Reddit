package com.emsi.springreddit.service;


import com.emsi.springreddit.dto.RegisterRequest;
import com.emsi.springreddit.repository.UserRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldRegisterNewUser(){
        String username = "testUser";
        RegisterRequest registerRequest = new RegisterRequest(username, "test@email.com", "testPassword");
        authService.signup(registerRequest);

        //Usernames are unique, hence we can use it to verify the user's existence
        Assertions.assertTrue(userRepository.findByUsername(username).isPresent());
    }

}
