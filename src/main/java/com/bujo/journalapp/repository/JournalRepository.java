package com.bujo.journalapp.repository;

import com.bujo.journalapp.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JournalRepository extends MongoRepository<JournalEntry, String> {
    void deleteById(String id);
    Optional<JournalEntry> findById(String id);
}
