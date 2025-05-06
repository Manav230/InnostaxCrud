package com.example.crud.Controller;

import com.example.crud.Dao.UserRepository;
import com.example.crud.Model.User;
import com.example.crud.Service.UserService;
import com.example.crud.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(TestConfig.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    public void testGetAllUsers() throws Exception {
        // Create first user
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice@example.com");

        // Create second user
        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        user2.setEmail("bob@example.com");

        userService.save(user1);
        userService.save(user2);

        List<User> users = userController.getAllUsers();
        System.out.println(users);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());


    }

    @Test
    public void testGetUserById() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("mail@gmai.com");

        userService.save(user1);
        System.out.println(user1);

        when(userService.findById(1)).thenReturn(user1);


        mockMvc.perform(get("/"+user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("mail@gmai.com"));

    }


}
