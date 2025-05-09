package com.example.crud.Dao.secondary;

import com.example.crud.Model.secondary.Vault;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaultRepository extends JpaRepository<Vault, Long> {

}