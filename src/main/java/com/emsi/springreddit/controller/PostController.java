package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.GenericResponse;
import com.emsi.springreddit.dto.request.PostRequest;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.emsi.springreddit.controller.SubredditController.getGenericResponseResponseEntity;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
@CrossOrigin(originPatterns = "*")
public class PostController {
    private final PostService postService;
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> getPostById(@PathVariable Long id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post found",
                            null,
                            postService.getPostById(id)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> createPost(@RequestBody PostRequest postRequest) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post created",
                            null,
                            postService.createPost(postRequest, user)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post updated",
                            null,
                            postService.updatePost(id, postRequest, user)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> deletePost(@PathVariable Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            postService.deletePost(id, user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post deleted",
                            null,
                            null
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @GetMapping(path = "/sub-name/{name}", produces = "application/json")
    public ResponseEntity<GenericResponse> getPostsBySubredditName(@PathVariable String name){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Posts found",
                            null,
                            postService.getPostsBySubredditName(name)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PostMapping(path = "/upvote/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> upvotePost(@PathVariable Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            postService.upvotePost(id, user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post upvoted",
                            null,
                            null
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PostMapping(path = "/downvote/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> downvotePost(@PathVariable Long id) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            postService.downvotePost(id, user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Post downvoted",
                            null,
                            null
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }


    private ResponseEntity<GenericResponse> handleExceptions(Exception exception) {
        return getGenericResponseResponseEntity(exception);
    }
}
