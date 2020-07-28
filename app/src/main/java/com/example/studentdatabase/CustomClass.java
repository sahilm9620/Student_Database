package com.example.studentdatabase;

public class CustomClass {
    private String name;
    private String rollno;
    private int profile_image;
    private  String email;
    private String password;

    public CustomClass(String rollno, String name, String email, String password, int profile_image) {
        this.rollno=rollno;
        this.name=name;
        this.email=email;
        this.password=password;
        this.profile_image=profile_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
