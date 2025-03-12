package com.bujo.journalapp.controllers;

import com.bujo.journalapp.entity.JournalEntry;
import com.bujo.journalapp.services.JournalServices;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class Journal {

    @Autowired
    private JournalServices journalServices;

    @GetMapping("/")
    public ResponseEntity<List<JournalEntry>> getJournal() {
        List<JournalEntry> journalEntries = journalServices.getAllEntries();
        if (CollectionUtils.isEmpty(journalEntries)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(journalEntries, HttpStatus.OK);
    }

    @PostMapping("/add-entry")
    public ResponseEntity<?> addEntry(@RequestBody JournalEntry journalEntry) {
        boolean entryAdded = journalServices.addEntry(journalEntry);
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

    @DeleteMapping("/id/{entry-id}")
    public ResponseEntity<?> deleteEntry(@PathVariable("entry-id") String entryId) {
        boolean isDeleted = journalServices.removeEntryById(entryId);
        if (!isDeleted) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Deleted from list", HttpStatus.OK);
    }

    @PutMapping("/id/{entry-id}")
    public ResponseEntity<JournalEntry> updateEntry(@PathVariable("entry-id") String entryId, @RequestBody JournalEntry journalEntry) {
        JournalEntry updatedEntry = journalServices.getEntryById(entryId);
        if (ObjectUtils.isEmpty(updatedEntry)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedEntry, HttpStatus.CREATED);
    }
}
