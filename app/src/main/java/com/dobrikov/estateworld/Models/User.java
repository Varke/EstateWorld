package com.dobrikov.estateworld.Models;

public class User {
    private String login, mail, password;

    public User() {}

    public User(String login, String mail, String password) {
        this.login = login;
        this.mail = mail;
        this.password = password;
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
}
