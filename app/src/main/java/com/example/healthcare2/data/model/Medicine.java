package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;

public class Medicine {
    @SerializedName("idMedicine")
    private int idMedicine;
    @SerializedName("idDoctor")
    private int idDoctor;
    @SerializedName("doctor")
    private Doctor doctor;
    @SerializedName("nameMedicine")
    private String nameMedicine;
    @SerializedName("img")
    private String img;
    @SerializedName("price")
    private float price;
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

    public Medicine(int idMedicine, Doctor doctor, String name, String img, float price, String content, int like, float rating, Timestamp created_at, Timestamp updated_at) {
        this.idMedicine = idMedicine;
        this.doctor = doctor;
        this.nameMedicine = name;
        this.img = img;
        this.price = price;
        this.content = content;
        this.like = like;
        this.rating = rating;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Medicine(int idDoctor, String nameMedicine, String img, float price, String content) {
        this.idDoctor = idDoctor;
        this.nameMedicine = nameMedicine;
        this.img = img;
        this.price = price;
        this.content = content;
    }

    public int getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(int idMedicine) {
        this.idMedicine = idMedicine;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getNameMedicine() {
        return nameMedicine;
    }

    public void setNameMedicine(String nameMedicine) {
        this.nameMedicine = nameMedicine;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
        return "Medicine{" +
                "idMedicine=" + idMedicine +
                ", doctor=" + doctor +
                ", nameMedicine='" + nameMedicine + '\'' +
                ", img='" + img + '\'' +
                ", price=" + price +
                ", content='" + content + '\'' +
                ", like=" + like +
                ", rating=" + rating +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
