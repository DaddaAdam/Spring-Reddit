package com.emsi.springreddit.service;

import com.emsi.springreddit.entities.User;
import com.emsi.springreddit.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow();
    }
}
