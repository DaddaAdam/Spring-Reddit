package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.GenericResponse;
import com.emsi.springreddit.dto.request.CommentRequest;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.emsi.springreddit.controller.SubredditController.getGenericResponseResponseEntity;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> getCommentById(@PathVariable Long id){
        try {
            return ResponseEntity
                    .status(200)
                    .body(new GenericResponse(
                            200,
                            "Comment Found",
                            null,
                            commentService.getCommentById(id)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> createComment(@RequestBody CommentRequest commentRequest){
        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity
                    .status(200)
                    .body(new GenericResponse(
                            200,
                            "Comment Created",
                            null,
                            commentService.createComment(commentRequest.getText(), commentRequest.getPostId(), user)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PatchMapping(path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> updateComment(@PathVariable Long id, @RequestBody CommentRequest commentRequest){
        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return ResponseEntity
                    .status(200)
                    .body(new GenericResponse(
                            200,
                            "Comment Updated",
                            null,
                            commentService.updateComment(commentRequest.getText(), id, user)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> deleteComment(@PathVariable Long id){
        try {
            var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            commentService.deleteComment(id, user);
            return ResponseEntity
                    .status(200)
                    .body(new GenericResponse(
                            200,
                            "Comment Deleted",
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
