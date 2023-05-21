package com.emsi.springreddit.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emsi.springreddit.entities.Comment;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.repository.CommentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    
    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElseThrow();        
    }

    @Transactional
    public void createComment(String text, Long postId, User user){
        if (text == null || text.isEmpty()) throw new DataIntegrityViolationException("Comment text cannot be empty");
        var comment = new Comment();
        comment.setText(text);
        comment.setPost(postService.getPostById(postId));
        comment.setUser(user);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(String text, Long commentId, User user){
        if (text == null || text.isEmpty()) throw new DataIntegrityViolationException("Comment text cannot be empty");
        var comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().equals(user)) throw new RuntimeException("Comment can only be updated by its owner");
        comment.setText(text);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, User user){
        var comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().equals(user)) throw new RuntimeException("Comment can only be deleted by its owner");
        commentRepository.delete(comment);
    }
}
