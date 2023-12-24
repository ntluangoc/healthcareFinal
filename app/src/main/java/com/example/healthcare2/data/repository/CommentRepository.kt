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

class CommentRepository (application: Application) {
    private var commentMutableLiveData = MutableLiveData<List<Comment>>()
    private var listComment: List<Comment> = ArrayList<Comment>()
    private var numCommentMutableLiveData = MutableLiveData<Int>()
    private var isCommentMedicineMutableLiveData = MutableLiveData<Int>()
    fun getCommentNotification(idUser: Int) : MutableLiveData<List<Comment>>{
        val apiService = RetrofitInstance.getApiService()
        val call: Call<List<Comment>> = apiService.getListCommentNotification(idUser)
        call.enqueue(object : Callback<List<Comment>>{
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.isSuccessful){
                    listComment = response.body()!!
                    commentMutableLiveData.setValue(listComment)
//                    Log.d("SUCCESS", listComment.toString())
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("ERROR", "msg list comment notification: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return commentMutableLiveData
    }
//  Comment Medicine
    fun getNumAllCommentMedicine(idMedicine: Int) : MutableLiveData<Int> {
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getNumAllCommentMedicine(idMedicine)
        call.enqueue(object : Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                numCommentMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                numCommentMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return numCommentMutableLiveData
    }
    fun getCommentMecicidne(idMedicine: Int) : MutableLiveData<List<Comment>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Comment>> = apiService.getCommentMedicine(idMedicine)
        call.enqueue(object: Callback<List<Comment>>{
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.body() != null) {
                    listComment = response.body()!!
                    commentMutableLiveData.setValue(listComment)
                    Log.d("SUCCESS", listComment.toString())
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return commentMutableLiveData
    }
    fun getIsCommentMedicine(idMedicine: Int, idUser: Int) : MutableLiveData<Int>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getIsCommentMedicine(idMedicine,idUser)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                isCommentMedicineMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                isCommentMedicineMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return isCommentMedicineMutableLiveData
    }
    fun addCommentMedicine(comment: Comment) {
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addCommentMedicine(comment)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add comment medicine: " + response.body().toString())
                    getCommentMecicidne(comment.idMedicine)
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
//  Comment Post
    fun getNumAllCommentPost(idPost: Int) : MutableLiveData<Int> {
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getNumAllCommentPost(idPost)
        call.enqueue(object : Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                numCommentMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                numCommentMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return numCommentMutableLiveData
    }
    fun getCommentPost(idPost: Int) : MutableLiveData<List<Comment>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Comment>> = apiService.getCommentPost(idPost)
        call.enqueue(object: Callback<List<Comment>>{
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                if (response.body() != null) {
                    listComment = response.body()!!
                    commentMutableLiveData.setValue(listComment)
                    Log.d("SUCCESS", listComment.toString())
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return commentMutableLiveData
    }
    fun getIsCommentPost(idPost: Int, idUser: Int) : MutableLiveData<Int>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getIsCommentPost(idPost,idUser)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                isCommentMedicineMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                isCommentMedicineMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return isCommentMedicineMutableLiveData
    }
    fun addCommentPost(comment: Comment) {
        val apiService = RetrofitInstance.getApiService()
        val call : Call<ResponseBody> = apiService.addCommentPost(comment)
        call.enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("SUCCESS", "Add comment medicine: " + response.body().toString())
                    getCommentPost(comment.idMedicine)
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
}