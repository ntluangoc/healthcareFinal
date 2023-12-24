package com.example.healthcare2.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.model.Post
import com.example.healthcare2.data.repository.PostRepository
import com.example.healthcare2.data.repository.PostRepository.PostCallback

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository: PostRepository = PostRepository(application)
    private val addPostResult: MutableLiveData<String> = MutableLiveData<String>()
    fun getAddPostResult(): LiveData<String> {
        return addPostResult
    }
    fun getNumPostUser(idUser: Int) : LiveData<Int> { return postRepository.getNumPostUser(idUser)}
    fun getListNumPostDoctor() : LiveData<List<Int>> { return postRepository.getListNumPostDoctor()}
    fun getPost(idPost: Int) : LiveData<Post> { return postRepository.getPost(idPost)}
    fun addPost(post: Post) {
        postRepository.addPost(post, object :PostCallback{
            override fun onPostAddedSuccessfully(status: String) {
                addPostResult.value = status
                Log.d("SUCCESS", "Add post: $status")
            }

            override fun onPostAddFailed(error: String) {
                addPostResult.value = error
                Log.d("ERROR", "Add post: $error")

            }

        })
    }
    fun getListPost() : MutableLiveData<List<Post>> {
        return postRepository.getListPost()
    }
    fun getTop3Post() : MutableLiveData<List<Post>> {
        return postRepository.getTop3Post()
    }
    fun getListPostUser(idUser: Int) : MutableLiveData<List<Post>> {
        return postRepository.getListPostUser(idUser)
    }
    fun searchPost(title: String) : MutableLiveData<List<Post>> {
        return postRepository.searchPost(title)
    }
}