package com.emsi.springreddit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String postName;
    private String url;
    @Lob
    private String description;
    private Integer voteCount = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subreddit subreddit;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, targetEntity = Comment.class)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, targetEntity = Vote.class)
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
