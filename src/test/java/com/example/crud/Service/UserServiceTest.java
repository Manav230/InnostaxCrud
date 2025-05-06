package com.example.crud.Service;

import com.example.crud.Dao.UserRepository;
import com.example.crud.Model.User;
import com.example.crud.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {

    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    public void save() {
//        User user = new User();
//        user.setId(5);
//        user.setFirstName("Manav");
//        user.setLastName("Walter");
//        user.setEmail("manav@gmail.com");
//
//
//         userService.save(user);
//
//    }

//    @Test
//    public void findAll() {
//        User user = new User();
//        user.setId(1);
//        user.setFirstName("Manav");
//        user.setLastName("Walter");
//        user.setEmail("k@gmail.com");
//        userService.save(user);
//
//        User user2 = new User();
//        user2.setId(2);
//        user2.setFirstName("Ma");
//        user2.setLastName("Walter");
//        user2.setEmail("mi@gmail.com");
//        userService.save(user2);
//        List<User> users = userService.findAll();
//
//    }

    @Test
    public void findId() {
        User user = new User();
        user.setId(1);
        user.setEmail("m@gmail.com");
        userService.save(user);
        System.out.println(user.getId());
        assertNotNull(userRepository.findById(1));
//        User user2 = userService.findById(3);
    }

    @Test
    public void delete() {
        User user2 = new User();
        user2.setId(91);
        user2.setEmail("mi@gmail.com");
        userService.save(user2);
        System.out.println(userRepository.findById(user2.getId()));
        assertNotNull(userRepository.findById(91));
    }

//    @Test
//    public void update() {
//        User user = new User();
//        user.setId(5);
//        user.setFirstName("Mnav");
//        user.setLastName("lter");
//        user.setEmail("manav@gmail.com");
//        userService.save(user);
//
//        User update = new User();
//        update.setEmail("manbav@innsorax.com");
//        update.setFirstName("kanav");
//        update.setLastName("sharma");
//        userService.update(user.getId(), update);
//    }

//    @Test
//    public void testByIdNotFound() {
//
//       User saved=  userService.findById(8585);
//        if(saved == null){
//            System.out.println("User not found");
//        }
//    }

}
