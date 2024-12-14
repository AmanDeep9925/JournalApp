package com.bujo.journalapp.services;

import com.bujo.journalapp.entity.JournalEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JournalServices {

    private Map<Long,JournalEntry> journalEntries = new HashMap<>();

    public  List<JournalEntry> getAllEntries() {
        return new ArrayList<>(journalEntries.values());
    }

    public boolean addEntry(JournalEntry entry) {
        journalEntries.put(entry.getId(), entry);
        return true;
    }

    public JournalEntry getEntryById(long id) {
        return journalEntries.get(id);
    }

    public boolean removeEntryById(long id) {
        return journalEntries.remove(id) != null;
    }

    public boolean updateEntry(long entryId, JournalEntry entry) {
        return journalEntries.put(entryId, entry) != null;
    }
}
