package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;

public class Post {
    @SerializedName("idPost")
    private int idPost;
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("user")
    private User user;
    @SerializedName("title")
    private String title;
    @SerializedName("img")
    private String img;
    @SerializedName("content")
    private String content;
    @SerializedName("like")
    private int like;
    @SerializedName("rating")
    private float rating;
    @SerializedName("created_at")
    private Timestamp created_at;
    @SerializedName("updated_at")
    private Timestamp updated_at;

    public Post(int idPost, int idUser, String title, String img, String content, int like, float rating, Timestamp created_at, Timestamp updated_at) {
        this.idPost = idPost;
        this.idUser = idUser;
        this.title = title;
        this.img = img;
        this.content = content;
        this.like = like;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post(int idPost, int idUser, User user, String title, String img, String content, int like, float rating, Timestamp created_at, Timestamp updated_at) {
        this.idPost = idPost;
        this.idUser = idUser;
        this.user = user;
        this.title = title;
        this.img = img;
        this.content = content;
        this.like = like;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Post(int idUser, String title, String content) {
        this.idUser = idUser;
        this.title = title;
        this.content = content;
    }

    public Post(int idUser, String title, String img, String content) {
        this.idUser = idUser;
        this.title = title;
        this.img = img;
        this.content = content;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
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
        return "Post{" +
                "idPost=" + idPost +
                ", idUser=" + idUser +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", img='" + img + '\'' +
                ", content='" + content + '\'' +
                ", like=" + like +
                ", rating=" + rating +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
