package com.bujo.journalApp.controllers;

import com.bujo.journalApp.entity.JournalEntry;
import com.bujo.journalApp.services.JournalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class Journal {

    @Autowired
    private JournalServices journalServices;

    @GetMapping("/")
    public List<JournalEntry> getJournal() {
        return journalServices.getAllEntries();
    }

    @PostMapping("/add-entry")
    public boolean addEntry(@RequestBody JournalEntry journalEntry) {
        return journalServices.addEntry(journalEntry);
    }

    @GetMapping("/id")
    public JournalEntry getJournalEntry(@RequestParam("entry_id") Long entryId) {
        return journalServices.getEntryById(entryId);
    }

    @DeleteMapping("/id/{entry-id}")
    public boolean deleteEntry(@PathVariable("entry-id") Long entryId) {
        return journalServices.removeEntryById(entryId);
    }

    @PutMapping("/id/{entry-id}")
    public boolean updateEntry(@PathVariable("entry-id") Long entryId, @RequestBody JournalEntry journalEntry) {
        return journalServices.updateEntry(entryId, journalEntry);
    }
}
