package net.engineeringdigest.journalApp.controller;

//import com.fasterxml.jackson.annotation.JsonAnyGetter;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepo;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/get")
    public ResponseEntity<?> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("getting user: " + auth.getName());
         return ResponseEntity.ok(this.userService.findAll());
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        System.out.println("in updateUser method");
      Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUsername(userName);
//        if(userInDb != null){
            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.updateEntry(userInDb);
//            return new ResponseEntity<>(userInDb, HttpStatus.OK);
//        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUsername(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> getUsers() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse != null) {
            greeting = ", Weather feels like  "+weatherResponse.getCurrent().getFeelslike();
        }
        return ResponseEntity.ok("Hi " + authentication.getName()+greeting);
    }
}
