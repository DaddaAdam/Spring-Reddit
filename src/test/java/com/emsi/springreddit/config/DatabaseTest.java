package com.emsi.springreddit.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseTest {

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.url}")
    private String url;


    @Test
    void contextLoads() {
    }

    @Test
    void testDbConnection(){
        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            assertNotNull(conn);
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }

}
