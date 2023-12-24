package com.example.healthcare2.data.model;

import com.google.gson.annotations.SerializedName;

public class Like {
    @SerializedName("idLike")
    private int idLike;
    @SerializedName("idUser")
    private int idUser;
    @SerializedName("idPost")
    private int idPost;
    @SerializedName("idMedicine")
    private int idMedicine;

    public Like(int idLike, int idUser, int idPost, int idMedicine) {
        this.idLike = idLike;
        this.idUser = idUser;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
    }



    public Like(int idUser, int idPost, int idMedicine) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.idMedicine = idMedicine;
    }

    public int getIdLike() {
        return idLike;
    }

    public void setIdLike(int idLike) {
        this.idLike = idLike;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    @Override
    public String toString() {
        return "Like{" +
                "idLike=" + idLike +
                ", idUser=" + idUser +
                ", idPost=" + idPost +
                ", idMedicine=" + idMedicine +
                '}';
    }
}
