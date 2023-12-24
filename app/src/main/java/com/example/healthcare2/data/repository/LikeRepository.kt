package com.example.healthcare2.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.api.RetrofitInstance
import com.example.healthcare2.data.model.Like
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LikeRepository(application: Application) {
    private var isLikeMutableLiveData = MutableLiveData<Int>()
    fun getIsLikeMedicine(idMedicine: Int,email: String) : MutableLiveData<Int>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getIsLikeMedicine(idMedicine,email)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                isLikeMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                isLikeMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return isLikeMutableLiveData
    }
    fun addLike(like: Like){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addLike(like)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add like medicine: " + response.body().toString())

                } else {
                    Log.d("ERROR", "Unsuccessful response add like: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg add like medicine: " + t.message)
                println("Lỗi: " + t.message)

            }
        })
    }
    fun deleteLike(like: Like){
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.deleteLike(like)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Delete like medicine: " + response.body().toString())

                } else {
                    Log.d("ERROR", "Unsuccessful response delete like: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("ERROR", "msg delete like medicine: " + t.message)
                println("Lỗi: " + t.message)

            }
        })
    }
}