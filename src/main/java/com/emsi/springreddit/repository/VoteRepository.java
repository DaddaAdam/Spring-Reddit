package com.emsi.springreddit.repository;

import com.emsi.springreddit.entities.Post;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User currentUser);
}
