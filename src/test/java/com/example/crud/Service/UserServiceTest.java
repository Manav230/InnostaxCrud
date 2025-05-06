package com.example.crud.Service;

import com.example.crud.Model.User;
import com.example.crud.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setId(5);
        user.setFirstName("Manav");
        user.setLastName("Walter");
        user.setEmail("manav@gmail.com");


         userService.save(user);

    }

    @Test
    public void findAll() {
        User user = new User();
        user.setId(1);
        user.setFirstName("Manav");
        user.setLastName("Walter");
        user.setEmail("k@gmail.com");
        userService.save(user);

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Ma");
        user2.setLastName("Walter");
        user2.setEmail("mi@gmail.com");
        userService.save(user2);
        List<User> users = userService.findAll();

    }

    @Test
    public void findId() {
        User user2 = userService.findById(2);
    }

//    @Test
//    public void delete() {
//        userService.delete(2);
//    }

    @Test
    public void update() {
        User user = new User();
        user.setId(5);
        user.setFirstName("Mnav");
        user.setLastName("lter");
        user.setEmail("manav@gmail.com");
        userService.save(user);

        User update = new User();
        update.setEmail("manbav@innsorax.com");
        update.setFirstName("kanav");
        update.setLastName("sharma");
        userService.update(user.getId(), update);
    }

    @Test
    public void testByIdNotFound() {

       User saved=  userService.findById(8585);
        if(saved == null){
            System.out.println("User not found");
        }
    }

}
