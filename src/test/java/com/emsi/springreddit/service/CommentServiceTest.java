package com.emsi.springreddit.service;

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
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetCommentById(){
        Assertions.assertNotNull(commentService.getCommentById(1L));
    }

    @Test
    public void shouldGetCommentsByPostId(){
        Assertions.assertEquals(
                3,
                commentService.getPostComments(1L).size()
        );
    }

    @Test
    public void shouldCreateComment(){
        Assertions.assertNotNull(commentService.createComment("Hello", 1L, userRepository.findByUsername("admin").orElseThrow()));
    }

    @Test
    public void shouldUpdateComment(){
        Assertions.assertNotNull(
                commentService.updateComment(
                        "0000",
                        1L,
                        userRepository.findByUsername("admin").orElseThrow()
                )
        );
    }

    @Test
    public void shouldDeleteComment() {
        Assertions.assertTrue(
                commentService.deleteComment(
                        1L,
                        userRepository.findByUsername("admin").orElseThrow()
                )
        );
    }

}
