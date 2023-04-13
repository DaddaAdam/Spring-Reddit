package com.emsi.springreddit.repository;

import com.emsi.springreddit.entities.Post;
import com.emsi.springreddit.entities.Subreddit;
import com.emsi.springreddit.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
