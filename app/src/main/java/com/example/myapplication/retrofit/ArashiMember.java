package com.example.myapplication.retrofit;

public class ArashiMember {
    public String status;
    public String User;
    public String Pass;

    public ArashiMember(String status, String user, String pass) {
        this.status = status;
        User = user;
        Pass = pass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
