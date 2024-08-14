package net.engineeringdigest.journalApp.controller;

//import com.fasterxml.jackson.annotation.JsonAnyGetter;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryContorllerV2 {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping()
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = userService.findByUsername(authentication.getName());

        List<JournalEntry> list =u.getJournalEntries();
        if(list != null && list.size() > 0){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try{

//        myEntry.setDate(LocalDateTime.now());
        System.out.println("i m in controllerr");
        journalEntryService.saveEntry(myEntry,authentication.getName());
        return new ResponseEntity<JournalEntry>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println(e);
        return new ResponseEntity<JournalEntry>(HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getById( @PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
       Optional<JournalEntry> journalEntry= user.getJournalEntries().stream().filter(j->j.getId().equals(id)).findFirst();
//        Optional<JournalEntry> journalEntry= journalEntryService.findById(id);
        if(journalEntry.isPresent()){
            return new ResponseEntity<JournalEntry>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<JournalEntry>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        Optional<JournalEntry> journalEntry= user.getJournalEntries().stream().filter(j->j.getId().equals(id)).findFirst();
        JournalEntry old = journalEntry.get();
        if(old != null){
            old.setTitle(newEntry.getTitle() != null && newEntry.getTitle().length() > 0 ? newEntry.getTitle() : old.getTitle());
            old.setContent(newEntry.getContent() !=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
        journalEntryService.saveEntry(old);
        return new ResponseEntity<>(old, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
          boolean removed=  journalEntryService.deleteEntryById(id, userName);
          if(removed)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
          else{
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
          }
    }
}
