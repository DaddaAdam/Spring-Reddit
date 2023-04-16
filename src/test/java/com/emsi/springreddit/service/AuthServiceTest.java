package com.emsi.springreddit.service;


import com.emsi.springreddit.dto.request.RegisterRequest;
import com.emsi.springreddit.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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
