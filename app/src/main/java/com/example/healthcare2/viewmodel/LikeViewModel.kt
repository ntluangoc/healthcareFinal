package com.example.healthcare2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.healthcare2.data.model.Like
import com.example.healthcare2.data.repository.LikeRepository

class LikeViewModel(application: Application) : AndroidViewModel(application) {
    private val likeRepository: LikeRepository = LikeRepository(application)
    fun getIsLikeMedicine(idMedicine: Int, email: String) : LiveData<Int> {return likeRepository.getIsLikeMedicine(idMedicine, email)}
    fun addLike(like: Like) { return likeRepository.addLike(like)}
    fun deleteLike(like: Like) { return likeRepository.deleteLike(like)}
}