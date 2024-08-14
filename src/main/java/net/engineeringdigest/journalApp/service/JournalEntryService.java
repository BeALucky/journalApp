package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;




    @Transactional
    public void saveEntry(JournalEntry entry, String userName) {
    try{

        User user = userService.findByUsername(userName);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved =   journalEntryRepo.save(entry);
        user.getJournalEntries().add(saved);
//        user.setUsername(null);
        userService.saveUser(user);
    }catch (Exception e){
        log.error(e.getMessage());

        throw new RuntimeException("An error occured while saving the entry", e);
    }
    }


    public void saveEntry(JournalEntry entry) {
//        User user = userService.findByUsername(userName);
        entry.setDate(LocalDateTime.now());
        JournalEntry saved =   journalEntryRepo.save(entry);
//        user.getJournalEntries().add(saved);
//        userService.saveEntry(user);
    }

    public List<JournalEntry> findAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteEntryById(ObjectId id, String userName) {
        boolean removed= false;
        try {
            User user = userService.findByUsername(userName);
             removed = user.getJournalEntries().removeIf(e->e.getId().equals(id));
            if(removed){
             userService.saveUser(user);
             journalEntryRepo.deleteById(id);
             return true;
            }
            return removed;
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while deleting the entry", e);
        }

    }
}
