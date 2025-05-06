package com.example.crud.Controller;

import com.example.crud.Dao.UserRepository;
import com.example.crud.Model.User;
import com.example.crud.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/createUser")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable int id) {
         userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User user) {
        return userService.update(id,user);
    }
}
