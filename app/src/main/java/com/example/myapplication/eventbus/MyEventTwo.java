package com.example.myapplication.eventbus;

public class MyEventTwo {
    private String Message;

    public MyEventTwo(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
