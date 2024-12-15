package com.bujo.journalapp.controllers;

import com.bujo.journalapp.entity.JournalEntry;
import com.bujo.journalapp.services.JournalServices;
import org.bson.types.ObjectId;
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
    public JournalEntry getJournalEntry(@RequestParam("entry_id") String entryId) {
        return journalServices.getEntryById(entryId);
    }

    @DeleteMapping("/id/{entry-id}")
    public boolean deleteEntry(@PathVariable("entry-id") String entryId) {
        return journalServices.removeEntryById(entryId);
    }

    @PutMapping("/id/{entry-id}")
    public JournalEntry updateEntry(@PathVariable("entry-id") String entryId, @RequestBody JournalEntry journalEntry) {
        return journalServices.updateEntry(entryId, journalEntry);
    }
}
