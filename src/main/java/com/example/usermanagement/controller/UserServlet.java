package com.example.usermanagement.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.usermanagement.dao.UserDAO;
import com.example.usermanagement.model.User;


public class UserServlet extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
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

        // Conversion de la date
        LocalDate naissance = LocalDate.parse(dateNaissance);

        // Création de l'objet User
        User user = new User(0, name, email, phone, naissance);
        userDAO.add(user);

        // Redirection vers /users
        resp.sendRedirect("users");
    }
}
