package org.aman.journalapp.service;

import org.aman.journalapp.Repository.JournalEntryRepo;
import org.aman.journalapp.entity.JournalEntry;
import org.aman.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User userdb = userService.findUserByUsername(userName)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            userdb.getJournalEntries().add(saved);
            userService.saveUser(userdb);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Error saving journal entry", e);
        }
    }
    public void saveEntry(JournalEntry journalEntry){
                   journalEntryRepo.save(journalEntry);
             }

    public List<JournalEntry> getAllJournalEntries(){
        return journalEntryRepo.findAll();
    }


    public Optional<JournalEntry> getEntryById(ObjectId id){
       return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteEntry(ObjectId id, String userName){
        boolean removed = false;
        try {

            User userdb = userService.findUserByUsername(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
            removed = userdb.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if(removed){
                userService.saveUser(userdb);
                journalEntryRepo.deleteById(id);
            }

        }catch (Exception e){
            System.out.println("Error deleting journal entry");
        throw new RuntimeException("Error deleting journal entry", e);
        } return removed;

    }


}
