package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void updateEntry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Arrays.asList("USER"));
        userRepo.save(user);
//        userRepo.save(user);
    }

    public void saveUser(User user){
        userRepo.save(user);;
    }
    public boolean saveNewUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(Arrays.asList("USER"));
            userRepo.save(user);
            return true;
        } catch (Exception e) {
            logger.error("Error Occured for {} :", user.getUsername(), e);
//            logger.warn("Something went wrong");
//            logger.info("Something went wrong");
//            logger.debug("Something went wrong");
//            logger.trace("Something went wrong");
            return false;
        }
    }

    public List<User> findAll() {

        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepo.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        userRepo.deleteById(id);
//        return true;
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }
}
