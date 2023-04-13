package com.emsi.springreddit;

import com.emsi.springreddit.entities.Post;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.emsi.springreddit.repository.PostRepository;

import java.time.Instant;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    @Order(1)
    public void shouldAddPost(){
        Post post = new Post();
        postRepository.save(post);

        post = new Post("TestPost", "url", "description", 0, null, Instant.now(), null);
        postRepository.save(post);
    }

    @Test
    @Order(2)
    @Transactional
    public void shouldGetAllPosts(){
        List<Post> posts = postRepository.findAll();

        for(Post post : posts){
            System.out.println(post);
        }
    }

    @Test
    @Order(3)
    public void shouldGetPostById(){
        Long postId = postRepository.findAll().get(1).getId();
        Assertions.assertTrue(postRepository.findById(postId).isPresent());
    }

    @Test
    @Order(4)
    public void shouldDeletePostById(){
        Long postId = postRepository.findAll().get(1).getId();
        postRepository.deleteById(postId);

        Assertions.assertFalse(postRepository.findById(postId).isPresent());
    }

    @Test
    @Order(5)
    public void shouldDeleteAllPosts(){
        postRepository.deleteAll();

        int postsLength = postRepository.findAll().size();
        Assertions.assertEquals(0, postsLength);
    }
}
