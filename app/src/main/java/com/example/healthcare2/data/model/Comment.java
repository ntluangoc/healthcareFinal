package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Comment {
    @SerializedName("idComment")
    private int idComment;
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("user")
    private User user;
    @SerializedName("idPost")
    private Integer idPost;
    @SerializedName("idMedicine")
    private Integer idMedicine;
    @SerializedName("content")
    private String content;
    @SerializedName("isCheck")
    private boolean isCheck;
    @SerializedName("created_at")
    private Timestamp created_at;
    @SerializedName("updated_at")
    private Timestamp updated_at;

    public Comment(int idComment, int idUser, User user, int idPost, int idMedicine, String content, boolean isCheck, Timestamp created_at, Timestamp updated_at) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.user = user;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
        this.content = content;
        this.isCheck = isCheck;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }



    public Comment(int idUser, Integer idPost, Integer idMedicine, String content) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
        this.content = content;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public Integer getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(int idMedicine) {
        this.idMedicine = idMedicine;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "idComment=" + idComment +
                ", idUser=" + idUser +
                ", user=" + user +
                ", idPost=" + idPost +
                ", idMedicine=" + idMedicine +
                ", content='" + content + '\'' +
                ", isCheck=" + isCheck +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
