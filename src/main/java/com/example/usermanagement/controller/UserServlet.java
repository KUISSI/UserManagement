package com.example.usermanagement.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.example.usermanagement.dao.IUserDAO;
import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;

@WebServlet("/users")
public class UserServlet extends HttpServlet {

    private IUserDAO userDAO;

    public UserServlet() {
        // Utilisé en production
        this.userDAO = new UserDAO();
    }

    // ✅ Pour les tests unitaires
    public UserServlet(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<User> users = userDAO.listAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("listUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String dateNaissance = req.getParameter("dateNaissance");

        LocalDate naissance = LocalDate.parse(dateNaissance);
        User user = new User(0, name, email, phone, naissance);
        userDAO.add(user);

        resp.sendRedirect("users");
    }

    // Optionnel pour test ou debug
    public IUserDAO getUserDAO() {
        return userDAO;
    }
}
