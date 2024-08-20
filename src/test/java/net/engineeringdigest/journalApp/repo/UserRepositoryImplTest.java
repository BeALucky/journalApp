package net.engineeringdigest.journalApp.repo;

import com.mongodb.assertions.Assertions;
import net.engineeringdigest.journalApp.repository.UserRepositoryImpl;
//import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {

    @Autowired
    UserRepositoryImpl userRepository;

    @Test
    public void testUserSA() {
        Assertions.assertNotNull(userRepository.getUserForSA());
    }
}
