package com.example.usermanagement.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class User {
    private int id;
    private String name;
    private String email;
    private String phone;
    private LocalDate dateNaissance;

    // Constantes pour la validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_REGEX = "^\\+?[0-9. ()-]{10,}$";
    private static final int AGE_MINIMUM = 18;

    // Constructeurs existants
    public User() {}

    public User(int id, String name, String email, String phone, LocalDate dateNaissance) {
        this.setId(id);
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        this.setDateNaissance(dateNaissance);
    }

    // Getters et setters améliorés avec validation
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("L'ID ne peut pas être négatif");
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide");
        }
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.matches(EMAIL_REGEX)) {
            throw new IllegalArgumentException("Format d'email invalide");
        }
        this.email = email.toLowerCase().trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone != null && !phone.matches(PHONE_REGEX)) {
            throw new IllegalArgumentException("Format de téléphone invalide");
        }
        this.phone = phone != null ? phone.trim() : null;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        if (dateNaissance == null) {
            throw new IllegalArgumentException("La date de naissance est obligatoire");
        }
        if (Period.between(dateNaissance, LocalDate.now()).getYears() < AGE_MINIMUM) {
            throw new IllegalArgumentException("L'âge minimum requis est de " + AGE_MINIMUM + " ans");
        }
        this.dateNaissance = dateNaissance;
    }

    // Méthodes utilitaires
    public int getAge() {
        return Period.between(dateNaissance, LocalDate.now()).getYears();
    }

    public boolean isAdult() {
        return getAge() >= AGE_MINIMUM;
    }

    // Méthodes equals et hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && 
               Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    // toString amélioré
    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s', email='%s', phone='%s', dateNaissance=%s, age=%d}",
                id, name, email, phone, dateNaissance, getAge());
    }
}