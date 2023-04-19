package com.emsi.springreddit.controller;

import com.emsi.springreddit.service.JwtService;
import com.emsi.springreddit.service.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = "classpath:insert.sql")
public class SubredditControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    public void shouldNotPostSubredditReasonNotAuthenticated() throws Exception {
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"test\", \"description\": \"test\"}")
        ).andExpect(status().isForbidden());
    }

    @Test
    @Order(2)
    public void shouldNotGetSubredditReasonNotAuthenticated() throws Exception {
        mockMvc.perform(
                get("/api/subreddit/java")
        ).andExpect(status().isForbidden());
    }

    @Test
    @Order(3)
    public void shouldGetSubreddit() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                get("/api/subreddit/java")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(4)
    public void shouldPostSubreddit() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"test\", \"description\": \"test\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void shouldNotPostSubredditReasonAlreadyExists() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"test\", \"description\": \"test\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(6)
    public void shouldNotPostSubredditReasonInvalidName() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"\", \"description\": \"test\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    public void shouldNotPostSubredditReasonInvalidDescription() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"test\", \"description\": \"\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    public void shouldNotGetSubredditReasonDoesNotExist() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                get("/api/subreddit/doesnotexist")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void shouldNotPostSubredditReasonInvalidJson() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                post("/api/subreddit")
                        .contentType("application/json")
                        .content("{\"name\": \"test\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    public void shouldPatchSubreddit() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"testx\", \"description\": \"testx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(11)
    public void shouldNotPatchSubredditReasonDoesNotExist() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                patch("/api/subreddit/100")
                        .contentType("application/json")
                        .content("{\"name\": \"testx\", \"description\": \"testx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(12)
    public void shouldPatchSubredditChangeOwner() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"testxx\", \"description\": \"testxx\", \"newOwner\": \"user\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(13)
    public void shouldNotPatchSubredditReasonNotOwner() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"testxx\", \"description\": \"testxx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isForbidden());
    }

    @Test
    @Order(14)
    public void shouldNotPatchSubredditReasonInvalidJson() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("admin"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\" \"testxx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(15)
    public void shouldNotPatchSubredditReasonInvalidName() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"\", \"description\": \"testxx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(16)
    public void shouldNotPatchSubredditReasonNameAlreadyTaken() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"python\", \"description\": \"testxx\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @Order(17)
    public void shouldNotPatchSubredditReasonInvalidNewOwner() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                patch("/api/subreddit/1")
                        .contentType("application/json")
                        .content("{\"name\": \"testxx\", \"description\": \"testxx\", \"newOwner\": \"doesnotexist\"}")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(18)
    public void shouldDeleteSubreddit() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                delete("/api/subreddit/1")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isOk());
    }

    @Test
    @Order(19)
    public void shouldNotDeleteSubredditReasonDoesNotExist() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                delete("/api/subreddit/100")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isNotFound());
    }

    @Test
    @Order(20)
    public void shouldNotDeleteSubredditReasonNotOwner() throws Exception {
        String token = jwtService.generateToken(userService.getUserByUsername("user"));
        mockMvc.perform(
                delete("/api/subreddit/2")
                        .header("Authorization", "Bearer " + token)
        ).andExpect(status().isForbidden());
    }

}
