package com.example.healthcare2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.healthcare2.data.model.Comment
import com.example.healthcare2.data.model.Medicine
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.data.repository.CommentRepository

class CommentViewModel(application: Application) : AndroidViewModel(application) {
    private val commentRepository: CommentRepository = CommentRepository(application)
//  Comment Medicine
    fun getNumAllCommentMedicine(idMedicine: Int) : LiveData<Int> { return commentRepository.getNumAllCommentMedicine(idMedicine)}
    fun getCommentMedicine(idMedicine: Int) : LiveData<List<Comment>>{ return commentRepository.getCommentMecicidne(idMedicine)}
    fun getIsCommentMedicine(idMedicine: Int, idUser: Int) : LiveData<Int> { return commentRepository.getIsCommentMedicine(idMedicine, idUser)}
    fun addCommentMedicine(comment: Comment)  { return commentRepository.addCommentMedicine(comment)}
//  Comment Post
    fun getNumAllCommentPost(idPost: Int) : LiveData<Int> { return commentRepository.getNumAllCommentPost(idPost)}
    fun getCommentPost(idPost: Int) : LiveData<List<Comment>>{ return commentRepository.getCommentPost(idPost)}
    fun getIsCommentPost(idPost: Int, idUser: Int) : LiveData<Int> { return commentRepository.getIsCommentPost(idPost, idUser)}
    fun addCommentPost(comment: Comment)  { return commentRepository.addCommentPost(comment)}
    fun getListCommentNotification(idUser: Int) : LiveData<List<Comment>> { return commentRepository.getCommentNotification(idUser)}

}