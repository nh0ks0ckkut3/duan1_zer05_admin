package com.example.duan1_quanlysalon.model;

public class Slot {
    private String time;
    private String phoneNumber;
    private boolean ready;

    public Slot(String time) {
        this.time = time;
        this.ready = true;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}
