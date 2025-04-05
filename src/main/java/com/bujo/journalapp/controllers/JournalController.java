package com.bujo.journalapp.controllers;

import com.bujo.journalapp.entity.JournalEntry;
import com.bujo.journalapp.services.JournalServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalServices journalServices;

    @GetMapping("/{user-name}")
    public ResponseEntity<List<JournalEntry>> getJournal(@PathVariable("user-name") String userName) {
        List<JournalEntry> journalEntries = journalServices.getAllEntries(userName);
        if (CollectionUtils.isEmpty(journalEntries)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("/add-entry/{username}")
    public ResponseEntity<?> addEntry(@PathVariable("username") String userName, @RequestBody JournalEntry journalEntry) {
        boolean entryAdded = journalServices.addEntry(userName, journalEntry);
        if (!entryAdded) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Added to list",HttpStatus.CREATED);
    }

    @GetMapping("/id/{entry-id}")
    public ResponseEntity<JournalEntry> getJournalEntry(@PathVariable("entry-id") String entryId) {
        JournalEntry journalEntry = journalServices.getEntryById(entryId);
        if (ObjectUtils.isEmpty(journalEntry)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(journalEntry, HttpStatus.OK);
    }

    @DeleteMapping("/id/{user-name}/{entry-id}")
    public ResponseEntity<?> deleteEntry(@PathVariable("user-name") String userName, @PathVariable("entry-id") String entryId) {
        boolean isDeleted = journalServices.removeEntryById(userName, entryId);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("Deleted from list", HttpStatus.OK);
    }

    @PutMapping("/id/{user-name}/{entry-id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable("user-name") String userName, @PathVariable("entry-id") String entryId, @RequestBody JournalEntry journalEntry) {
        JournalEntry updatedEntry = journalServices.updateEntry(userName, entryId, journalEntry);
        if (ObjectUtils.isEmpty(updatedEntry)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(updatedEntry, HttpStatus.CREATED);
    }
}
