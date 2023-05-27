package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.PostRequest;
import com.emsi.springreddit.entities.Post;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final SubredditService subredditService;
    private final VoteService voteService;
    public Post getPostById(Long id){
        try {
            return postRepository.findById(id).orElseThrow();
        }
        catch (Exception exception){
            throw new NoSuchElementException("Post not found");
        }
    }

    @Transactional
    public Post createPost(PostRequest postRequest, User user){
        if (postRequest.getTitle() == null || postRequest.getTitle().isBlank()) throw new DataIntegrityViolationException("Post title is required");
        if (postRequest.getContent() == null || postRequest.getContent().isBlank()) throw new DataIntegrityViolationException("Post content is required");
        if (postRequest.getUrl() == null || postRequest.getUrl().isBlank()) throw new DataIntegrityViolationException("Post url is required");
        if (!UrlValidator.getInstance().isValid(postRequest.getUrl())) throw new DataIntegrityViolationException("Post url is invalid");

        var post = new Post();
        post.setPostName(postRequest.getTitle());
        post.setDescription(postRequest.getContent());
        post.setCreatedDate(java.time.Instant.now());
        post.setSubreddit(subredditService.getSubredditById(postRequest.getSubredditId()));
        post.setUrl(postRequest.getUrl());
        post.setUser(user);
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long id, PostRequest postRequest, User user){
        if (postRequest.getUrl() != null && !UrlValidator.getInstance().isValid(postRequest.getUrl())) throw new DataIntegrityViolationException("Post url is invalid");
        if (postRequest.getContent() != null && postRequest.getContent().isBlank()) throw new DataIntegrityViolationException("Post content is invalid");
        if (postRequest.getTitle() != null && postRequest.getTitle().isBlank()) throw new DataIntegrityViolationException("Post title is invalid");

        var post = postRepository.findById(id).orElseThrow();
        if(!post.getUser().getUsername().equals(user.getUsername())) throw new RuntimeException("You are not allowed to update this post");
        if (postRequest.getTitle() != null && !postRequest.getTitle().isBlank()) post.setPostName(postRequest.getTitle());
        if (postRequest.getContent() != null && !postRequest.getContent().isBlank()) post.setDescription(postRequest.getContent());
        if (postRequest.getUrl() != null && !postRequest.getUrl().isBlank()) post.setUrl(postRequest.getUrl());
        return postRepository.save(post);
    }

    @Transactional
    public boolean deletePost(Long id, User user){
        var post = postRepository.findById(id).orElseThrow();
        if(!post.getUser().getUsername().equals(user.getUsername())) throw new RuntimeException("You are not allowed to delete this post");
        postRepository.delete(post);
        return true;
    }

    public List<Post> getPostsBySubredditName(String subredditName){
        return postRepository.findAllBySubreddit(subredditService.getSubredditByName(subredditName));
    }

    @Transactional
    public void upvotePost(Long id, User user){
        var post = postRepository.findById(id).orElseThrow();
        post.setVoteCount(post.getVoteCount() + voteService.upvotePost(post, user));
        postRepository.save(post);
    }

    @Transactional
    public void downvotePost(Long id, User user){
        var post = postRepository.findById(id).orElseThrow();
        post.setVoteCount(post.getVoteCount() + voteService.downvotePost(post, user));
        postRepository.save(post);
    }
}
