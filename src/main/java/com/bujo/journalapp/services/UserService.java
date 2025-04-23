package com.bujo.journalapp.services;

import com.bujo.journalapp.entity.User;
import com.bujo.journalapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        return user.orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByUsername(String userName) {
        userRepository.deleteByUsername(userName);
    }

    public User updateUser(String userName, User user) {
        User oldUser = userRepository.findByUsername(userName).orElse(null);
        if ( !ObjectUtils.isEmpty(oldUser) ) {
            oldUser.setUsername(!ObjectUtils.isEmpty(user.getUsername()) ? user.getUsername() : oldUser.getUsername());
            oldUser.setPassword(!ObjectUtils.isEmpty(user.getPassword()) ? user.getPassword() : oldUser.getPassword());
            oldUser.setJournalEntries(!CollectionUtils.isEmpty(user.getJournalEntries()) ? user.getJournalEntries() : oldUser.getJournalEntries());
        }

        userRepository.save(oldUser);
        return oldUser;

    }

}
