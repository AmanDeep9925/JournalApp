package com.bujo.journalapp.services;

import com.bujo.journalapp.entity.JournalEntry;
import com.bujo.journalapp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class JournalServices {

    @Autowired
    private JournalRepository journalRepository;

    public  List<JournalEntry> getAllEntries() {
        return journalRepository.findAll();
    }

    public boolean addEntry(JournalEntry entry) {
        entry.setCreatedAt(LocalDateTime.now());
        journalRepository.save(entry);
        return true;
    }

    public JournalEntry getEntryById(String id) {
        Optional<JournalEntry> journalEntry =  journalRepository.findById(id);
        return journalEntry.orElse(null);
    }

    public boolean removeEntryById(String id) {
        journalRepository.deleteById(id);
        return true;
    }

    public JournalEntry updateEntry(String entryId, JournalEntry entry) {
        JournalEntry oldEntry = journalRepository.findById(entryId).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(entry.getTitle() != null && !Objects.equals(entry.getTitle(), "") ? entry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(entry.getContent() != null && !Objects.equals(entry.getContent(), "") ? entry.getContent() : oldEntry.getContent());
        }
        journalRepository.save(oldEntry);
        return oldEntry;
    }
}
