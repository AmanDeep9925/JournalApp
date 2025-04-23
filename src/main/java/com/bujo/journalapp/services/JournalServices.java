package com.bujo.journalapp.services;

import com.bujo.journalapp.entity.JournalEntry;
import com.bujo.journalapp.entity.User;
import com.bujo.journalapp.repository.JournalRepository;
import com.bujo.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalServices {

    private final JournalRepository journalRepository;
    private final UserService userService;

    public JournalServices(JournalRepository journalRepository, UserService userService) {
        this.journalRepository = journalRepository;
        this.userService = userService;
    }

    public  List<JournalEntry> getAllEntries (String userName) {
        User user = userService.getUserByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            return null;
        }
        return user.getJournalEntries();
    }

    @Transactional
    public List<?> addEntry(String username, JournalEntry entry) {
        try {
            User user = userService.getUserByUsername(username);
            if (ObjectUtils.isEmpty(user)) {
                return null;
            }
            entry.setCreatedAt(LocalDateTime.now());
            JournalEntry savedEntry = journalRepository.save(entry);
            user.getJournalEntries().add(savedEntry);
            userService.saveUser(user);
            return user.getJournalEntries();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addEntry(JournalEntry entry) {
        journalRepository.save(entry);
    }

    public JournalEntry getEntryById(String id) {
        Optional<JournalEntry> journalEntry =  journalRepository.findById(id);
        return journalEntry.orElse(null);
    }

    public boolean removeEntryById(String userName, String id) {
        User user = userService.getUserByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        }
        user.getJournalEntries().removeIf(entry -> entry.getId().equals(id));
        userService.saveUser(user);
        journalRepository.deleteById(id);
        return true;
    }

    public JournalEntry updateEntry(String userName, String entryId, JournalEntry entry) {
        JournalEntry oldEntry = journalRepository.findById(entryId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(entry.getTitle() != null && !Objects.equals(entry.getTitle(), "") ? entry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(entry.getContent() != null && !Objects.equals(entry.getContent(), "") ? entry.getContent() : oldEntry.getContent());
        }
        addEntry(oldEntry);
        return oldEntry;
    }
}
