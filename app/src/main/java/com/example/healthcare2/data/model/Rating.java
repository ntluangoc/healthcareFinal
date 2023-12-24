package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

public class Rating {
    @SerializedName("idRating")
    private int idRating;
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("idDoctor")
    private int idDoctor;
    @SerializedName("idPost")
    private int idPost;
    @SerializedName("idMedicine")
    private int idMedicine;
    @SerializedName("rating")
    private int rating;

    public Rating(int idRating, int idUser, int idDoctor, int idPost, int idMedicine, int rating) {
        this.idRating = idRating;
        this.idUser = idUser;
        this.idDoctor = idDoctor;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
        this.rating = rating;
    }



    public Rating(int idUser, int idDoctor, int idPost, int idMedicine, int rating) {
        this.idUser = idUser;
        this.idDoctor = idDoctor;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
        this.rating = rating;
    }


    public int getIdRating() {
        return idRating;
    }

    public void setIdRating(int idRating) {
        this.idRating = idRating;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public int getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(int idMedicine) {
        this.idMedicine = idMedicine;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "idRating=" + idRating +
                ", idUser=" + idUser +
                ", idDoctor=" + idDoctor +
                ", idPost=" + idPost +
                ", idMedicine=" + idMedicine +
                ", rating=" + rating +
                '}';
    }
}
