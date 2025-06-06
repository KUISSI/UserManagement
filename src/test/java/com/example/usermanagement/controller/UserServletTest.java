package com.example.usermanagement.controller;

import com.example.usermanagement.dao.IUserDAO;
import com.example.usermanagement.model.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

public class UserServletTest {

    @Mock
    private IUserDAO userDAO;

    @InjectMocks
    private UserServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new UserServlet(userDAO); // injection explicite
    }

    @Test
    public void testDoPost_shouldAddUserAndRedirect() throws Exception {
        // Arrange : on simule les paramètres reçus
        when(request.getParameter("name")).thenReturn("Alice");
        when(request.getParameter("email")).thenReturn("alice@example.com");
        when(request.getParameter("phone")).thenReturn("0102030405");
        when(request.getParameter("dateNaissance")).thenReturn("1990-01-01");

        // Act : on appelle la méthode doPost
        servlet.doPost(request, response);

        // Assert : on vérifie que l'utilisateur a été ajouté et redirection faite
        verify(userDAO, times(1)).add(argThat(user ->
            user.getName().equals("Alice") &&
            user.getEmail().equals("alice@example.com") &&
            user.getPhone().equals("0102030405") &&
            user.getDateNaissance().equals(LocalDate.of(1990, 1, 1))
        ));

        verify(response).sendRedirect("users");
    }
}
