package com.example.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.usermanagement.model.User;

public class UserDAO {

    // ✅ Nouveau chemin de base de données — à la racine du projet
    
    private final String DB_URL = "jdbc:sqlite:users.db";

    public UserDAO() {
        System.out.println("Base de données utilisée : " + DB_URL);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT," +
                     "email TEXT," +
                     "phone TEXT," +
                     "dateNaissance TEXT)";

        try {
            Class.forName("org.sqlite.JDBC"); // Chargement explicite du driver (nécessaire avec Tomcat)
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User u = new User(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    LocalDate.parse(rs.getString("dateNaissance"))
                );
                users.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void add(User user) {
        String sql = "INSERT INTO users(name, email, phone, dateNaissance) VALUES (?, ?, ?, ?)";
        Class.forName("org.sqlite.JDBC"); // Chargement explicite du driver (nécessaire avec Tomcat)
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getDateNaissance().toString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
