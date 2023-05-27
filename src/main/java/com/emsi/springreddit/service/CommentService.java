package com.emsi.springreddit.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.emsi.springreddit.entities.Comment;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.repository.CommentRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    public Comment getCommentById(Long id){
        return commentRepository.findById(id).orElseThrow();
    }

    public List<Comment> getPostComments(Long id){
        var post = postService.getPostById(id);
        return commentRepository.findAllByPostOrderByCreatedDateAsc(post);
    }

    @Transactional
    public Comment createComment(String text, Long postId, User user){
        if (text == null || text.isEmpty()) throw new DataIntegrityViolationException("Comment text cannot be empty");
        var comment = new Comment();
        comment.setText(text);
        comment.setPost(postService.getPostById(postId));
        comment.setUser(user);

        return commentRepository.save(comment);
    }

    @Transactional
    public Comment updateComment(String text, Long commentId, User user){
        if (text == null || text.isEmpty()) throw new DataIntegrityViolationException("Comment text cannot be empty");
        var comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getId().equals(user.getId())) throw new RuntimeException("Comment can only be updated by its owner");
        comment.setText(text);
        return commentRepository.save(comment);
    }

    @Transactional
    public boolean deleteComment(Long commentId, User user){
        var comment = commentRepository.findById(commentId).orElseThrow();
        if (!comment.getUser().getId().equals(user.getId())) throw new RuntimeException("Comment can only be deleted by its owner");
        commentRepository.delete(comment);
        return true;
    }
}
