package com.example.crud.Service;

import com.example.crud.Dao.primary.UserRepository;
import com.example.crud.Model.primary.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

 public boolean  delete(int id) {
     if (userRepository.existsById(id)) {
         userRepository.deleteById(id);
         return true;
     } else {
         return false;
     } }

    public boolean update(int id, User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setEmail(updatedUser.getEmail());
            // Add other fields as needed
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


}
