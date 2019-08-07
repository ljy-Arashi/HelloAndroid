package com.example.myapplication.eventbus;

public class MyEvent {
    private String Message;

    public MyEvent(String message) {
        Message = message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
