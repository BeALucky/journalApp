package net.engineeringdigest.journalApp.controller;

//import com.fasterxml.jackson.annotation.JsonAnyGetter;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/_journal")
public class JournalEntryContorller {




    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAllJournal(){
        return new ArrayList<>(journalEntries.values());
    }

    @PostMapping
    public boolean SaveJournalEntry(@RequestBody JournalEntry journalEntry){
//        journalEntries.put(journalEntry.getId(),journalEntry);
        return true;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getById( @PathVariable long id){
        return journalEntries.get(id);
    }

    @PutMapping("/id/{id}")
    public JournalEntry update(@PathVariable long id, @RequestBody JournalEntry journalEntry){
        journalEntries.put(id,journalEntry);
        return journalEntry;
    }

    @DeleteMapping("/id/{id}")
    public boolean delete(@PathVariable long id){
        journalEntries.remove(id);
        return true;
    }
}
