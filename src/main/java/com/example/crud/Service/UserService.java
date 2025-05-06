package com.example.crud.Service;

import com.example.crud.Dao.UserRepository;
import com.example.crud.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(int id) {
        return
                userRepository.findById(id).orElse(null);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User update(int id, User user) {
        userRepository.findById(user.getId()).orElse(null);

        user.setId(id);
        user.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        return userRepository.save(user);

    }

}
