package com.emsi.springreddit.repository;

import com.emsi.springreddit.entities.Comment;
import com.emsi.springreddit.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreatedDateAsc(Post post);
}
