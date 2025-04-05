package com.bujo.journalapp.repository;

import com.bujo.journalapp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    void deleteById(String id);
    void deleteByUsername(String username);
    Optional<User> findByUsername(String username);
}
