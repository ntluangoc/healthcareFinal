package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class User {
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("nameUser")
    private String nameUser;
    @SerializedName("birthday")
    private Date birhtday;
    @SerializedName("email")
    private String email;
    @SerializedName("avatar")
    private String avatar;

    public User() {
    }

    public User(String name, String email, Date birhtday) {
        this.nameUser = name;
        this.email = email;
        this.birhtday = birhtday;
    }

    public User(int idUser, String name, Date birhtday, String email, String avatar) {
        this.idUser = idUser;
        this.nameUser = name;
        this.birhtday = birhtday;
        this.email = email;
        this.avatar = avatar;
    }

    public User(String nameUser, String email, String avatar) {
        this.nameUser = nameUser;
        this.email = email;
        this.avatar = avatar;
    }

    public User(String nameUser, String email) {
        this.nameUser = nameUser;
        this.email = email;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirhtday() {
        return birhtday;
    }

    public void setBirhtday(Date birhtday) {
        this.birhtday = birhtday;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name='" + nameUser + '\'' +
                ", birhtday=" + birhtday +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
