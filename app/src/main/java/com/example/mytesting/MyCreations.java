package com.example.mytesting;

public class MyCreations {

    private String name;
    private String date;
    private String firstName;
    private String secondName;
    private String firstUrl;
    private String secondUrl;

    public MyCreations() {

    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getFirstUrl() {
        return firstUrl;
    }

    public String getSecondUrl() {
        return secondUrl;
    }

    public MyCreations(String name, String date, String firstName, String secondName, String firstUrl, String secondUrl) {
        this.name = name;
        this.date = date;
        this.firstName = firstName;
        this.secondName = secondName;
        this.firstUrl = firstUrl;
        this.secondUrl = secondUrl;
    }
}
