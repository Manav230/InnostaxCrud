package com.example.crud.Dao.primary;


import com.example.crud.Model.primary.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> id(int id);
    Optional<User> findByEmail(String email);
}
