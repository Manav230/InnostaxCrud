package com.example.crud.Controller;

import com.example.crud.Dao.UserRepository;
import com.example.crud.Model.User;
import com.example.crud.Service.UserService;
import com.example.crud.config.TestConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @BeforeAll
    public static void setUp() {
        System.out.println("Server Started");
    }

    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        user1.setId(1);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice@example.com");

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("Bob");
        user2.setLastName("Johnson");
        user2.setEmail("bob@example.com");

        List<User> Users = Arrays.asList(user1, user2);

        when(userService.findAll()).thenReturn(Users);

        mockMvc.perform(get("/"))  // Make sure this is your actual mapped endpoint
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[1].email").value("bob@example.com"));
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


        mockMvc.perform(get("/" + user1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("mail@gmai.com"));

    }

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        user.setFirstName("Manav");
        user.setLastName("Gandhi");
        user.setEmail("manav.gandhi@gmail.com");

        userService.save(user);

        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/createUser") // Replace with actual endpoint if different
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Manav"))
                .andExpect(jsonPath("$.lastName").value("Gandhi"))
                .andExpect(jsonPath("$.email").value("manav.gandhi@gmail.com"));
    }

    @Test
    public void testFullFlow() throws Exception {
        User user1 = new User();
        user1.setId(10);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice@gmail.com");

        String userJson = objectMapper.writeValueAsString(user1);
        when(userService.save(any(User.class))).thenReturn(user1);
        MvcResult createResult = mockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("alice@gmail.com"))
                .andReturn();

        // Get by id
        when(userService.findById(10)).thenReturn(user1);

        mockMvc.perform(get("/" + user1.getId()))
                .andExpect(jsonPath("$.id").value(user1.getId()))
                .andExpect(jsonPath("$.email").value("alice@gmail.com"))
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andReturn();

        when(userService.findAll()).thenReturn(Collections.singletonList(user1));

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Alice"))
                .andExpect(jsonPath("$[0].lastName").value("Smith"))
                .andExpect(jsonPath("$[0].email").value("alice@gmail.com"));


        // delete the user
        when(userService.delete(10)).thenReturn(true);

        mockMvc.perform(delete("/10"))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted"));

    }

    @Test
    public void createAndUpdateUser() throws Exception {
        User user1 = new User();
        user1.setId(10);
        user1.setFirstName("Alice");
        user1.setLastName("Smith");
        user1.setEmail("alice@gmail.com");

        userService.save(user1);

        when(userService.save(any(User.class))).thenReturn(user1);

        mockMvc.perform(post("/createUser") // Replace with actual endpoint if different
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("alice@gmail.com"));


        //update the user
        when(userService.update(eq(10), any(User.class))).thenReturn(true);

        mockMvc.perform(put("/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"manav\", \"email\": \"manav@gmail.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("User updated"));


    }

}
