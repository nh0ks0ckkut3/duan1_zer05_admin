package com.example.duan1_quanlysalon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {
    @SerializedName("userNameEmployee")
    private String userName;
    private String passWord;
    private String name;
    private int age;
    private String gender;
    private int salary;
    private String dayStartWork;
    private int countDayOfMonth;
    private String classify;

    public Employee(String userName, String passWord, String name, int age, String gender, int salary, String dayStartWork, int countDayOfMonth, String classify) {

        this.userName = userName;
        this.passWord = passWord;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.dayStartWork = dayStartWork;
        this.countDayOfMonth = countDayOfMonth;
        this.classify = classify;
    }

    public Employee(String userName, String passWord, String name, int age, String gender, String dayStartWork, String classify) {
        this.userName = userName;
        this.passWord = passWord;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.dayStartWork = dayStartWork;
        this.classify = classify;
        this.countDayOfMonth = 0;
        this.salary = 0;
    }

    public Employee(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getDayStartWork() {
        return dayStartWork;
    }

    public void setDayStartWork(String dayStartWork) {
        this.dayStartWork = dayStartWork;
    }

    public int getCountDayOfMonth() {
        return countDayOfMonth;
    }

    public void setCountDayOfMonth(int countDayOfMonth) {
        this.countDayOfMonth = countDayOfMonth;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }
}
