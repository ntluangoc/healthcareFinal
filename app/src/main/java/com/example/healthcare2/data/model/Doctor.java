package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;

public class Doctor extends User{
    @SerializedName("idDoctor")
    private int idDoctor;
    @SerializedName("description")
    private String description;

    @SerializedName("ratingDoctor")
    private float ratingDoctor;

    public Doctor(String name, String email, Date birhtday, float rating) {
        super(name, email, birhtday);
        this.ratingDoctor = rating;
    }

    public Doctor(String name, String email, Date birhtday, int idDoctor, String description, float rating) {
        super(name, email, birhtday);
        this.idDoctor = idDoctor;
        this.description = description;
        this.ratingDoctor = rating;
    }

    public Doctor(User user, String description) {
        super(user.getIdUser(), user.getNameUser(), user.getBirhtday(), user.getEmail(), user.getAvatar());
        this.description = description;
    }

    public Doctor(String description) {
        this.description = description;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRatingDoctor() {
        return ratingDoctor;
    }

    public void setRatingDoctor(float ratingDoctor) {
        this.ratingDoctor = ratingDoctor;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "idDoctor=" + idDoctor + ", " +
                super.toString() +
                ", description='" + description + '\'' +
                ", ratingDoctor=" + ratingDoctor +
                '}';
    }
}
