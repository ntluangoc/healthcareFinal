package com.example.healthcare2.data.api;

import androidx.annotation.Nullable;

import com.example.healthcare2.data.model.Comment;
import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.data.model.Like;
import com.example.healthcare2.data.model.Medicine;
import com.example.healthcare2.data.model.Post;
import com.example.healthcare2.data.model.Rating;
import com.example.healthcare2.data.model.User;
import com.example.healthcare2.data.model.Work;

import java.util.Date;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestApiService {
    @GET("userList")
    Call<List<User>> getUserList();

    @GET("doctor/{idUser}")
    Call<Doctor> getDoctor(@Path("idUser") int idUser);
//  Detail Medicine
    @GET("medicine/{idMedicine}")
    Call<Medicine> getMedicine(@Path("idMedicine") int idMedicine);
    @GET("numAllCommentMedicine/{idMedicine}")
    Call<Integer> getNumAllCommentMedicine(@Path("idMedicine") int idMedicine);
    @GET("commentMedicine/{idMedicine}")
    Call<List<Comment>> getCommentMedicine(@Path("idMedicine") int idMedicine);
    @GET("ratingCommentMedicine/{idMedicine}")
    Call<List<Rating>> getRatingCommentMedicine(@Path("idMedicine") int idMedicine);

    @POST("addCommentMedicine")
    Call<ResponseBody> addCommentMedicine(@Body Comment comment);
    @POST("addRatingMedicine")
    Call<ResponseBody> addRatingMedicine(@Body Rating rating);
    @GET("isCommentMedicine/{idMedicine}/{idUser}")
    Call<Integer> getIsCommentMedicine(@Path("idMedicine") int idMedicine, @Path("idUser") int idUser);
//  Medicine
    @GET("medicineList")
    Call<List<Medicine>> getMedicineList();
    @GET("searchMedicine/{name}")
    Call<List<Medicine>> searchMedicine(@Path("name") String name);
    @POST("addMedicine")
    Call<ResponseBody> addMedicine(@Body Medicine medicine);
//  User
    @POST("/addUser")
    Call<ResponseBody> addUser(@Body User user);
    @POST("/updateUser")
    Call<ResponseBody> updateUser(@Body User user);
    @GET("getUser/{email}")
    Call<User> getUser(@Path("email") String email);
//  Post
    @GET("/numPostUser/{idUser}")
    Call<Integer> getNumPostUser(@Path("idUser") int idUser);
    @GET("listNumPostDoctor")
    Call<List<Integer>> getListNumPostDoctor();
    @GET("post/{idPost}")
    Call<Post> getPost(@Path("idPost") int idPost);
    @POST("addPost")
    Call<ResponseBody> addPost(@Body Post post);
    @GET("listPost")
    Call<List<Post>> getListPost();
    @GET("top3Post")
    Call<List<Post>> getTop3Post();
    @GET("listPostUser/{idUser}")
    Call<List<Post>> getListPostUser(@Path("idUser")int idUser);
    @GET("searchPost/{title}")
    Call<List<Post>> searchPost(@Path("title") String title);
//  Detail Post
    @GET("numAllCommentPost/{idPost}")
    Call<Integer> getNumAllCommentPost(@Path("idPost") int idPost);
    @GET("commentPost/{idPost}")
    Call<List<Comment>> getCommentPost(@Path("idPost") int idPost);
    @GET("ratingCommentPost/{idPost}")
    Call<List<Rating>> getRatingCommentPost(@Path("idPost") int idPost);

    @POST("addCommentPost")
    Call<ResponseBody> addCommentPost(@Body Comment comment);
    @POST("addRatingPost")
    Call<ResponseBody> addRatingPost(@Body Rating rating);
    @GET("isCommentPost/{idPost}/{idUser}")
    Call<Integer> getIsCommentPost(@Path("idPost") int idPost, @Path("idUser") int idUser);
//  Like
    @GET("likeMedicine/{idMedicine}/{email}")
    Call<Integer> getIsLikeMedicine(@Path("idMedicine") int idMedicine, @Path("email") String email);
    @POST("addLike")
    Call<ResponseBody> addLike(@Body Like like);
    @POST("deleteLike")
    Call<ResponseBody> deleteLike(@Body Like like);
//  Notification
    @GET("listCommentNotification/{idUser}")
    Call<List<Comment>> getListCommentNotification(@Path("idUser") int idUser);
//  Doctor
    @GET("listDoctor")
    Call<List<Doctor>> getListDoctor();
    @POST("addDoctor")
    Call<ResponseBody> addDoctor(@Body Doctor doctor);
    @POST("updateDoctor")
    Call<ResponseBody> updateDoctor(@Body Doctor doctor);
    @GET("searchDoctor/{name}")
    Call<List<Doctor>> searchDoctor(@Path("name") String name);
    @POST("addRatingDoctor")
    Call<ResponseBody> addRatingDoctor(@Body Rating rating);
//  Work
    @GET("listWorkByDate/{idUser}/{date}")
    Call<List<Work>> getListWorkByDate(@Path("idUser") int idUser,@Path("date") String date);
    @GET("listWorkToday/{idUser}")
    Call<List<Work>> getListWorkToday(@Path("idUser") int idUser);
    @POST("updateIsCheckWork")
    Call<ResponseBody> updateIsCheckWork(@Body Work work);
    @POST("addWork")
    Call<ResponseBody> addWork(@Body Work work);
    @GET("nextWork/{idUser}")
    Call<List<Work>> getNextWork(@Path("idUser") int idUser);
//  Rating
    @POST("deleteRating")
    Call<ResponseBody> deleteRating(@Body Rating rating);
    @GET("rating/{idUser}/{idDoctor}")
    Call<Rating> getRating(@Path("idUser") int idUser, @Path("idDoctor") int idDoctor);
}
