package com.emsi.springreddit.entities;


import jakarta.persistence.*;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String postName;
    private String url;
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = LAZY)
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = LAZY)
    private Subreddit subreddit;

    @OneToMany(mappedBy = "post", fetch = LAZY, targetEntity = Comment.class)
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", fetch = LAZY, targetEntity = Vote.class)
    private List<Vote> votes;

    public Post(String postName, String url, String description, Integer voteCount, User user, Instant createdDate, Subreddit subreddit) {
        this.postName = postName;
        this.url = url;
        this.description = description;
        this.voteCount = voteCount;
        this.user = user;
        this.createdDate = createdDate;
        this.subreddit = subreddit;
    }
}
