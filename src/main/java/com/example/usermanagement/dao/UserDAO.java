// Fichier : src/main/java/com/example/usermanagement/dao/UserDAO.java
package com.example.usermanagement.dao;

import com.example.usermanagement.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    private final String DB_URL = "jdbc:sqlite:users.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("✅ Driver SQLite chargé.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Le driver JDBC SQLite est introuvable.", e);
        }
    }

    public UserDAO() {
        System.out.println("📂 Base de données utilisée : " + DB_URL);
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "name TEXT," +
                     "email TEXT," +
                     "phone TEXT," +
                     "dateNaissance TEXT)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("🧱 Table 'users' vérifiée/créée.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users(name, email, phone, dateNaissance) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getDateNaissance().toString());
            pstmt.executeUpdate();
            System.out.println("✅ Utilisateur ajouté : " + user.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
