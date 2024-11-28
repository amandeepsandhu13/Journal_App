package org.aman.journalapp.service;

import org.aman.journalapp.Repository.JournalEntryRepo;
import org.aman.journalapp.entity.JournalEntry;
import org.aman.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            User userdb = userService.findUserByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            userdb.getJournalEntries().add(saved);
            userService.createUser(userdb);
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

    public void deleteEntry(ObjectId id, String userName){
        User userdb = userService.findUserByUsername(userName);
        journalEntryRepo.deleteById(id);
        userdb.getJournalEntries().removeIf(x ->x.getId().equals(id));
        userService.createUser(userdb);
    }


}
