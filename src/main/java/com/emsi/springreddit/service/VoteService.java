package com.emsi.springreddit.service;

import com.emsi.springreddit.entities.Post;
import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.entities.Vote;
import com.emsi.springreddit.repository.VoteRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    @Transactional
    public int upvotePost(Post post, User user){
        return this.vote(post, user, 1);
    }

    @Transactional
    public int downvotePost(Post post, User user){
        return this.vote(post, user, -1);
    }


    private int vote(Post post, User user, int voteType){
        var vote = voteRepository.findByPostAndUser(post, user);
        if(vote.isPresent()){
            if(vote.get().getVoteType() == voteType) return 0;
            vote.get().setVoteType(voteType);
            voteRepository.save(vote.get());
            return voteType * 2;
        }
        voteRepository.save(new Vote(null, voteType, post, user));
        return voteType;
    }
}
