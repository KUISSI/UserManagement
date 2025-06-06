// Fichier : src/main/java/com/example/usermanagement/dao/IUserDAO.java
package com.example.usermanagement.dao;

import java.util.List;

import com.example.usermanagement.model.User;

public interface IUserDAO {
    List<User> listAll();
    void add(User user);
}
