package com.alaminkarno.fcmexample.models;

public class User {
    private String userId;
    private String email;
    private String fcmToken;

    public User(String userId, String email, String fcmToken) {
        this.userId = userId;
        this.email = email;
        this.fcmToken = fcmToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
