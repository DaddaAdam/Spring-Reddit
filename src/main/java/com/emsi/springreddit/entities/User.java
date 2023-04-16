package com.emsi.springreddit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Username is required")
    @Column(unique = true)
    private String username;
    @JsonIgnore
    @NotBlank(message = "Password is required")
    private String password;
    @Email
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;
    private Instant created;
    @JsonIgnore
    private boolean enabled;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Subreddit.class)
    @JsonIgnore
    private List<Subreddit> subreddits;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Post.class)
    @JsonIgnore
    private List<Post> posts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Comment.class)
    @JsonIgnore
    private List<Comment> comments;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Vote.class)
    @JsonIgnore
    private List<Vote> votes;
}