package com.example.healthcare2.data.repository

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.api.RetrofitInstance
import com.example.healthcare2.data.model.Post
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.IOException

class PostRepository(application: Application) {
    private var numPostMutableLiveData = MutableLiveData<Int>()
    private var listNumPostDoctorMutableLiveData = MutableLiveData<List<Int>>()
    private var postMutableLiveData = MutableLiveData<Post>()
    private val listPostMutableLiveData = MutableLiveData<List<Post>>()

    fun getNumPostUser(idUser: Int) : MutableLiveData<Int>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Int> = apiService.getNumPostUser(idUser)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                numPostMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                numPostMutableLiveData.value = 0
                Log.d("ERROR", "msg numAllCommentMedicine: " + t.message)
            }

        })
        return numPostMutableLiveData
    }
    fun getListNumPostDoctor() : MutableLiveData<List<Int>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Int>> = apiService.listNumPostDoctor
        call.enqueue(object : Callback<List<Int>>{
            override fun onResponse(call: Call<List<Int>>, response: Response<List<Int>>) {
                if (response.isSuccessful){
                    listNumPostDoctorMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "get list num post doctor successfully")
                }
            }

            override fun onFailure(call: Call<List<Int>>, t: Throwable) {
                Log.d("ERROR", "msg listNumPostDoctor: " + t.message)
            }

        })
        return listNumPostDoctorMutableLiveData
    }
    fun getPost(idPost: Int) : MutableLiveData<Post>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Post> = apiService.getPost(idPost)
        call.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                    postMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "get post successfully")
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("ERROR", "msg get post: " + t.message)
            }

        })
        return postMutableLiveData
    }
    interface PostCallback {
        fun onPostAddedSuccessfully(status: String)
        fun onPostAddFailed(error: String)
    }
    fun addPost(post: Post, callback:PostCallback) {
        val apiService = RetrofitInstance.getApiService()
        val call = apiService.addPost(post)

        call.enqueue(object : Callback<ResponseBody> {
            // Xử lý kết quả ở đây
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val responseString = response.body()!!.string()
                        val jsonObject = JSONObject(responseString)
                        val status = jsonObject.getString("status")
                        if (status == "success") {
                            callback.onPostAddedSuccessfully(status)
                        } else {
                            callback.onPostAddFailed("Failed to add doctor")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        callback.onPostAddFailed(e.message.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        callback.onPostAddFailed(e.message.toString())
                    }
                } else {
                    callback.onPostAddFailed("Add doctor error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getListPost() : MutableLiveData<List<Post>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Post>> = apiService.listPost
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (response.isSuccessful){
                    listPostMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list post successfully")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("ERROR", "msg get list post: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listPostMutableLiveData
    }
    fun getTop3Post() : MutableLiveData<List<Post>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Post>> = apiService.top3Post
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (response.isSuccessful){
                    listPostMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list post successfully")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("ERROR", "msg get list post: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listPostMutableLiveData
    }
    fun getListPostUser(idUser: Int) : MutableLiveData<List<Post>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Post>> = apiService.getListPostUser(idUser)
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (response.isSuccessful){
                    listPostMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list post successfully")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("ERROR", "msg get list post: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listPostMutableLiveData
    }
    fun searchPost(title: String) : MutableLiveData<List<Post>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Post>> = apiService.searchPost(title)
        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                if (response.isSuccessful){
                    listPostMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list post successfully")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.d("ERROR", "msg get list post: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listPostMutableLiveData
    }
}