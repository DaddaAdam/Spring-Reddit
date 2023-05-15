package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.PostRequest;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.NoSuchElementException;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql(scripts = "classpath:insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    public void shouldGetPostById(){
        Assertions.assertNotNull(postService.getPostById(1L));
    }

    @Test
    public void shouldNotGetPostByIdReasonNotFound(){
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.getPostById(100L));
    }

    @Test
    public void shouldCreatePost(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        postRequest.setSubredditId(1L);
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.createPost(postRequest, user));
    }



    @Test
    public void shouldNotCreatePostReasonInvalidSubreddit(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        postRequest.setSubredditId(100L);
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.createPost(postRequest, user));
    }

    @Test
    public void shouldNotCreatePostReasonInvalidUrl(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        postRequest.setSubredditId(1L);
        postRequest.setUrl("https://www.google");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(RuntimeException.class, () -> postService.createPost(postRequest, user));
    }

    @Test
    public void shouldNotCreatePostReasonInvalidTitle(){
        var postRequest = new PostRequest();
        postRequest.setTitle("");
        postRequest.setContent("Test content");
        postRequest.setSubredditId(1L);
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(RuntimeException.class, () -> postService.createPost(postRequest, user));
    }

    @Test
    public void shouldNotCreatePostReasonInvalidContent(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("");
        postRequest.setSubredditId(1L);
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(RuntimeException.class, () -> postService.createPost(postRequest, user));
    }

    @Test
    public void shouldPatchOnlyPostTitle(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchOnlyPostContent(){
        var postRequest = new PostRequest();
        postRequest.setContent("Test content");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchOnlyPostUrl(){
        var postRequest = new PostRequest();
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchPostTitleAndContent(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchPostTitleAndUrl(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchPostContentAndUrl(){
        var postRequest = new PostRequest();
        postRequest.setContent("Test content");
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldPatchPostTitleAndContentAndUrl(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        postRequest.setContent("Test content");
        postRequest.setUrl("https://www.google.com");
        var user = userService.getUserByUsername("admin");
        Assertions.assertNotNull(postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldNotPatchPostReasonInvalidTitle(){
        var postRequest = new PostRequest();
        postRequest.setTitle("");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldNotPatchPostReasonInvalidContent(){
        var postRequest = new PostRequest();
        postRequest.setContent("");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldNotPatchPostReasonInvalidUrl(){
        var postRequest = new PostRequest();
        postRequest.setUrl("https://www.google");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(RuntimeException.class, () -> postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldNotPatchPostReasonUserNotOwner(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        var user = userService.getUserByUsername("user");
        Assertions.assertThrows(RuntimeException.class, () -> postService.updatePost(1L, postRequest, user));
    }

    @Test
    public void shouldNotPatchPostReasonNotFound(){
        var postRequest = new PostRequest();
        postRequest.setTitle("Test title");
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.updatePost(100L, postRequest, user));
    }

    @Test
    public void shouldDeletePost(){
        var user = userService.getUserByUsername("admin");
        Assertions.assertTrue(postService.deletePost(1L, user));
    }

    @Test
    public void shouldNotDeletePostReasonUserNotOwner(){
        var user = userService.getUserByUsername("user");
        Assertions.assertThrows(RuntimeException.class, () -> postService.deletePost(1L, user));
    }

    @Test
    public void shouldNotDeletePostReasonNotFound(){
        var user = userService.getUserByUsername("admin");
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.deletePost(100L, user));
    }

    @Test
    public void shouldGetAllPostsBySubredditName(){
        var subredditName = "java";
        var posts = postService.getPostsBySubredditName(subredditName);
        Assertions.assertEquals(4, posts.size());
    }

    @Test
    public void shouldNotGetAllPostsBySubredditNameReasonNotFound(){
        var subredditName = "java1";
        Assertions.assertThrows(NoSuchElementException.class, () -> postService.getPostsBySubredditName(subredditName));
    }

    @Test
    public void shouldUpvotePost(){
        var user = userService.getUserByUsername("user2");
        postService.upvotePost(1L, user);
        var post = postService.getPostById(1L);
        Assertions.assertEquals(2, post.getVoteCount());
    }

    @Test
    public void shouldNotUpvotePostReasonAlreadyUpvoted(){
        var user = userService.getUserByUsername("admin");
        postService.upvotePost(1L, user);
        var post = postService.getPostById(1L);
        Assertions.assertEquals(1, post.getVoteCount());
    }

    @Test
    public void shouldDownvotePost(){
        var user = userService.getUserByUsername("admin");
        postService.downvotePost(1L, user);
        var post = postService.getPostById(1L);
        Assertions.assertEquals(-1, post.getVoteCount());
    }

    @Test
    public void shouldNotDownvotePostReasonAlreadyDownvoted(){
        var user = userService.getUserByUsername("user2");
        postService.downvotePost(1L, user);
        var post = postService.getPostById(1L);
        Assertions.assertEquals(1, post.getVoteCount());
    }
}
