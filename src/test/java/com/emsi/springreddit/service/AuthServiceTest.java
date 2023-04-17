package com.emsi.springreddit.service;


import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.exception.UserAlreadyExistsException;
import com.emsi.springreddit.repository.UserRepository;
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
    public void shouldRegisterNewUser() throws UserAlreadyExistsException {
        String username = "testUser12345";
        RegisterRequest registerRequest = new RegisterRequest(username, "test12345@email.com", "testPassword");
        authService.signup(registerRequest);

        //Usernames are unique, hence we can use it to verify the user's existence
        Assertions.assertTrue(userRepository.findByUsername(username).isPresent());
    }

}
