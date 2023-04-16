package com.emsi.springreddit.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Subreddit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private String description;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "subreddit", fetch = FetchType.LAZY, targetEntity = Post.class)
    @JsonIgnore
    private List<Post> posts;
}