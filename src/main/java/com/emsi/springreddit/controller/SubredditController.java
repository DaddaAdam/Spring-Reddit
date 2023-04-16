package com.emsi.springreddit.controller;

import com.emsi.springreddit.dto.GenericResponse;
import com.emsi.springreddit.dto.request.SubredditRequest;
import com.emsi.springreddit.repository.UserRepository;
import com.emsi.springreddit.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {
    private final SubredditService subredditService;

    // TODO: MUST BE REMOVED AND GET THE USER FROM THE CONTEXT
    private final UserRepository userRepository;

    @GetMapping(path = "/{name}", produces = "application/json")
    public ResponseEntity<GenericResponse> getSubredditByUsername(@PathVariable String name){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Subreddit found",
                            null,
                            subredditService.getSubredditByName(name)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> createSubreddit(@RequestBody SubredditRequest subredditRequest) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Subreddit created",
                            null,
                            // TODO: MUST GET THE USER FROM THE CONTEXT
                            subredditService.createSubreddit(subredditRequest,
                                    userRepository.findAll().get(0)
                            )
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @PatchMapping (path = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GenericResponse> updateSubreddit(@RequestBody SubredditRequest subredditRequest, @PathVariable Long id) {
        try {
            // TODO: MUST VERIFY IF THE USER IS THE OWNER OF THE SUBREDDIT
            if (false) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Subreddit updated",
                            null,
                            subredditService.updateSubreddit(subredditRequest, id)
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse> deleteSubreddit(@PathVariable Long id) {
        try {
            // TODO: MUST VERIFY IF THE USER IS THE OWNER OF THE SUBREDDIT
            if (false) {
                throw new HttpClientErrorException(HttpStatus.FORBIDDEN);
            }
            subredditService.deleteSubreddit(id);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new GenericResponse(
                            HttpStatus.OK.value(),
                            "Subreddit deleted",
                            null,
                            null
                    ));
        }
        catch (Exception exception){
            return this.handleExceptions(exception);
        }
    }

    private ResponseEntity<GenericResponse> handleExceptions(Exception exception) {
        return switch (exception.getClass().getName()) {
            case "org.springframework.dao.DataIntegrityViolationException" -> ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            exception.getMessage(),
                            "Bad Request",
                            null
                    ));
            case "java.util.NoSuchElementException" -> ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse(
                            HttpStatus.NOT_FOUND.value(),
                            exception.getMessage(),
                            "Not Found",
                            null
                    ));
            case "org.springframework.web.client.HttpClientErrorException" -> ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new GenericResponse(
                            HttpStatus.FORBIDDEN.value(),
                            "You are not the owner of this subreddit",
                            "Forbidden",
                            null
                    ));
            default -> ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error",
                            "Internal Server Error",
                            null
                    ));
        };
    }
}
