package com.example.duan1_quanlysalon.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Employee implements Serializable {
    @SerializedName("userNameEmployee")
    private String userName;
    private String passWord;
    private String name;
    private int age;
    private String birthDay;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    private int salary;
    private String dayStartWork;
    private int countDayOfMonth;
    private String classify;
    private String img;

    public Employee(String userName, String passWord, String name, String birthDay, String phoneNumber, String email, String address, String gender, int salary, String dayStartWork, String classify, String img) {
        this.userName = userName;
        this.passWord = passWord;
        this.name = name;
        this.birthDay = birthDay;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.gender = gender;
        this.salary = salary;
        this.dayStartWork = dayStartWork;
        this.classify = classify;
        this.img = img;
    }

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

//    employee.setName(edName.getText().toString());
//                employee.setBirthDay(edBirthDay.getText().toString());
//                employee.setGender(edGender.getText().toString());
//                employee.setPhoneNumber(edPhoneNumber.getText().toString());
//                employee.setAddress(edAddress.getText().toString());
//                employee.setClassify(edClassify.getText().toString());
//                employee.setDayStartWork(edDateStart.getText().toString());
//                employee.setSalary(Integer.parseInt(edSalary.getText().toString()));
    public Employee (String userName, String name, String birthDay, String gender, String phoneNumber, String address, String classify, String dayStartWork, int salary){
        this.userName = userName;
        this.name = name;
        this.birthDay = birthDay;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.classify = classify;
        this.dayStartWork = dayStartWork;
        this.salary = salary;
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

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
