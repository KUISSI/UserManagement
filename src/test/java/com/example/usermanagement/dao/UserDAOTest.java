package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    private UserDAO userDAO;

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO(); // Vérifie que DB est créée correctement
    }

    @Test
    void testAddAndListAllUsers() {
        User user = new User(0, "Alice", "alice@example.com", "0102030405", LocalDate.of(1990, 1, 1));
        userDAO.add(user);

        List<User> users = userDAO.listAll();
        assertFalse(users.isEmpty(), "La liste des utilisateurs ne doit pas être vide.");
        
        boolean found = users.stream().anyMatch(u -> 
            u.getName().equals("Alice") &&
            u.getEmail().equals("alice@example.com")
        );
        assertTrue(found, "L'utilisateur ajouté doit apparaître dans la liste.");
    }
}
