package com.emsi.springreddit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DatabaseTest {

    @Test
    void contextLoads() {
    }

    @Test
    void testDbConnection(){
        String username = System.getProperty("spring.datasource.username");
        String password = System.getProperty("spring.datasource.password");
        String url = System.getProperty("spring.datasource.url");

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            assertNotNull(conn);
        } catch (SQLException e) {
            fail("Connection failed: " + e.getMessage());
        }
    }
}
