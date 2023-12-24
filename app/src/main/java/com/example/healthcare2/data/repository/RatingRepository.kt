package com.example.healthcare2.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.api.RetrofitInstance
import com.example.healthcare2.data.model.Comment
import com.example.healthcare2.data.model.Rating
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingRepository (application: Application) {
    private var ratingUser = MutableLiveData<Rating>()
    private var ratingMutableLiveData = MutableLiveData<List<Rating>>()
    private var listRating: List<Rating> = ArrayList<Rating>()
    fun getTop3RatingCommentMedicine(idMedicine: Int) : MutableLiveData<List<Rating>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Rating>> = apiService.getRatingCommentMedicine(idMedicine)
        call.enqueue(object : Callback<List<Rating>>{
            override fun onResponse(call: Call<List<Rating>>, response: Response<List<Rating>>) {
                if (response.body() != null) {
                    listRating = response.body()!!
                    ratingMutableLiveData.setValue(listRating)
                    Log.d("SUCCESS", listRating.toString())
                }
            }

            override fun onFailure(call: Call<List<Rating>>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return ratingMutableLiveData
    }
    fun addRatingMedicine(rating: Rating){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addRatingMedicine(rating)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add rating medicine: " + response.body().toString())
                    getTop3RatingCommentMedicine(rating.idMedicine)
                } else {
                    Log.d("ERROR", "Unsuccessful response: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
    }
    fun getTop3RatingCommentPost(idPost: Int) : MutableLiveData<List<Rating>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Rating>> = apiService.getRatingCommentPost(idPost)
        call.enqueue(object : Callback<List<Rating>>{
            override fun onResponse(call: Call<List<Rating>>, response: Response<List<Rating>>) {
                if (response.body() != null) {
                    listRating = response.body()!!
                    ratingMutableLiveData.setValue(listRating)
                    Log.d("SUCCESS", listRating.toString())
                }
            }

            override fun onFailure(call: Call<List<Rating>>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return ratingMutableLiveData
    }
    fun addRatingPost(rating: Rating){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addRatingPost(rating)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add rating post: " + response.body().toString())
                    getTop3RatingCommentPost(rating.idPost)
                } else {
                    Log.d("ERROR", "Unsuccessful response: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
    }
    fun addRatingDoctor(rating: Rating){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addRatingDoctor(rating)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add rating doctor: " + response.body().toString())

                } else {
                    Log.d("ERROR", "Unsuccessful doctor: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
    }
    fun deleteRating(rating: Rating){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.deleteRating(rating)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Delete rating doctor: " + response.body().toString())

                } else {
                    Log.d("ERROR", "Unsuccessful doctor: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
    }
    fun getRating(idUser: Int, idDoctor: Int) : MutableLiveData<Rating>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Rating> = apiService.getRating(idUser, idDoctor)
        call.enqueue(object : Callback<Rating>{
            override fun onResponse(call: Call<Rating>, response: Response<Rating>) {
                if (response.isSuccessful){
                    ratingUser.value = response.body()
                    Log.d("SUCCESS", "rating user: " + response.body().toString())
                }
            }

            override fun onFailure(call: Call<Rating>, t: Throwable) {
                Log.d("ERROR", "msg get rating: " + t.message)
            }

        })
        return ratingUser
    }
}