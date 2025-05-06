package com.example.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@TestConfiguration
public class TestConfig {

 @Autowired
 private DataSource dataSource;

    @PostConstruct
    public void initializeSchema() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "email VARCHAR(255) NOT NULL, " +
                    "first_name VARCHAR(255), " +
                    "last_name VARCHAR(255))");

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize test schema", e);
        }
    }
}
