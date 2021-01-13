package com.dobrikov.estateworld.Models;

public class User {
    private String login, mail, password, number, favoriteApartments ="";

    public User() {}

    public User(String login, String mail, String password, String number, String favoriteApartments) {
        this.login = login;
        this.mail = mail;
        this.password = password;
        this.number = number;
        this.favoriteApartments = favoriteApartments;
    }

    public String getFavoriteApartments() {
        return favoriteApartments;
    }

    public void setFavoriteApartments(String favoriteApartments) {
        this.favoriteApartments = favoriteApartments;
    }

    public void addFavoriteApartment(String apartmentIdAsString) {
        this.favoriteApartments = this.favoriteApartments.concat(apartmentIdAsString + " ");
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
