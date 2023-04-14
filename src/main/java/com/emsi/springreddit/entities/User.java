package com.emsi.springreddit.entities;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;
    private Instant created;
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Subreddit.class)
    private List<Subreddit> subreddits;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Post.class)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Comment.class)
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Vote.class)
    private List<Vote> votes;
}