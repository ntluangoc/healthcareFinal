package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Work {
    @SerializedName("idWork")
    private int idWork;
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("title")
    private String title;
    @SerializedName("time")
    private Timestamp time;
    @SerializedName("note")
    private String note;
    @SerializedName("isCheck")
    private boolean isCheck;

    public Work(int idWork, int idUser, String title, Timestamp time, String note, boolean isCheck) {
        this.idWork = idWork;
        this.idUser = idUser;
        this.title = title;
        this.time = time;
        this.note = note;
        this.isCheck = isCheck;
    }

    public Work(int idUser, String title, Timestamp time, String note) {
        this.idUser = idUser;
        this.title = title;
        this.time = time;
        this.note = note;
    }

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "Work{" +
                "idWork=" + idWork +
                ", idUser=" + idUser +
                ", title=" + title +
                ", time=" + time +
                ", note='" + note + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }
}
