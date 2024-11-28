package org.aman.journalapp.controller;

import org.aman.journalapp.entity.JournalEntry;
import org.aman.journalapp.entity.User;
import org.aman.journalapp.service.JournalEntryService;
import org.aman.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesByUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findUserByUsername(userName);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<JournalEntry> journalEntries = user.getJournalEntries();
        if(journalEntries.size() > 0){
            return new ResponseEntity<>(journalEntries, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createJournal(@RequestBody JournalEntry newEntry, @PathVariable String userName){
        try{
            journalEntryService.saveEntry(newEntry, userName);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{myId}/{userName}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId, @PathVariable String userName){
        journalEntryService.deleteEntry(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{myId}/{userName}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId myId,
                                                        @RequestBody JournalEntry newEntry,
                                                        @PathVariable String userName){
        JournalEntry old = journalEntryService.getEntryById(myId).orElse(null);
        if(old != null){
            old.setName(newEntry.getName() != null ? newEntry.getName() : old.getName());
            old.setContent(newEntry.getContent() != null ? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(old, HttpStatus.NOT_FOUND);

    }

}
