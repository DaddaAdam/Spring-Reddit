package com.emsi.springreddit.service;

import com.emsi.springreddit.dto.request.SubredditRequest;
import com.emsi.springreddit.entities.Subreddit;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.repository.SubredditRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class SubredditService {
    private final SubredditRepository subredditRepository;
    private final UserService userService;

    public Subreddit getSubredditByName(String subredditName){
        try {
            return subredditRepository.findByName(subredditName).orElseThrow();
        }
        catch (NoSuchElementException exception){
            throw new NoSuchElementException("Subreddit not found");
        }
    }

    public Subreddit getSubredditById(Long id){
        try {
            return subredditRepository.findById(id).orElseThrow();
        }
        catch (NoSuchElementException exception){
            throw new NoSuchElementException("Subreddit not found");
        }
    }

    @Transactional
    public Subreddit createSubreddit(SubredditRequest subredditRequest, User user){
        var subreddit = new Subreddit();
        if (subredditRequest.getName() != null && subredditRequest.getName().isBlank())
            throw new DataIntegrityViolationException("Subreddit name cannot be empty");

        subreddit.setName(subredditRequest.getName() != null ? subredditRequest.getName().replaceAll("\\s", "") : null);
        subreddit.setDescription(subredditRequest.getDescription());
        subreddit.setCreatedDate(Instant.now());
        subreddit.setUser(user);
        try {
            return subredditRepository.save(subreddit);
        }
        catch (DataIntegrityViolationException exception){
            if(Objects.requireNonNull(exception.getRootCause()).getMessage().startsWith("Duplicate entry")){
                throw new DataIntegrityViolationException("Subreddit already exists");
            }
            throw new DataIntegrityViolationException(exception.getRootCause().getMessage());
        }
    }

    @Transactional
    public Subreddit updateSubreddit(SubredditRequest subredditRequest, Long subredditId){
        var subreddit = subredditRepository.findById(subredditId).orElseThrow();
        if (subredditRequest.getName() != null) {
            if (subredditRequest.getName().isBlank())
                throw new DataIntegrityViolationException("Subreddit name cannot be empty");
            subreddit.setName(subredditRequest.getName().replaceAll("\\s", ""));
        }
        if (subredditRequest.getDescription() != null) subreddit.setDescription(subredditRequest.getDescription());
        if (subredditRequest.getNewOwner() != null){
            try {
                subreddit.setUser(userService.getUserByUsername(subredditRequest.getNewOwner()));
            }
            catch (NoSuchElementException exception){
                throw new NoSuchElementException("User not found");
            }
        }
        return subredditRepository.save(subreddit);
    }

    @Transactional
    public void deleteSubreddit(Long subredditId){
        var subreddit = subredditRepository.findById(subredditId).orElseThrow();
        subredditRepository.delete(subreddit);
    }
}
