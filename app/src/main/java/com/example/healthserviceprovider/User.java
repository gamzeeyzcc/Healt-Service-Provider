package com.example.healthserviceprovider;

public class User {
    private int id;
    private String identity;
    private String password;
    private String namesurname;
    //User nesnesi için yardımcı sınıf
    public User() {
    }

    public User(int id, String identity, String password, String namesurname) {
        this.id = id;
        this.identity = identity;
        this.password = password;
        this.namesurname = namesurname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamesurname() {
        return namesurname;
    }

    public void setNamesurname(String namesurname) {
        this.namesurname = namesurname;
    }
}
