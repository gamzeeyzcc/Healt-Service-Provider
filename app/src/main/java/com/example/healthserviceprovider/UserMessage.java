package com.example.healthserviceprovider;

public class UserMessage {
    private int id;
    private int userId;
    private int providerId;
    private String message;

    private String sender;
    //Yardımcı UserMEssage sınıfı, kullanıcıların gönderdği ve aldığı mesajlar için kullanılır
    public UserMessage() {
    }

    public UserMessage(int id, int userId, int providerId, String message, String sender) {
        this.id = id;
        this.userId = userId;
        this.providerId = providerId;
        this.message = message;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
