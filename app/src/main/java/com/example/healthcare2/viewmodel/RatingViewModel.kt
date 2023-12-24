package com.example.healthcare2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.data.repository.RatingRepository

class RatingViewModel (application: Application) : AndroidViewModel(application) {
    private val ratingRepository: RatingRepository = RatingRepository(application)
    fun getRatingCommentMedicine(idMedicine: Int) : LiveData<List<Rating>>{ return ratingRepository.getTop3RatingCommentMedicine(idMedicine)}
    fun addRatingMedicine(rating: Rating) {return ratingRepository.addRatingMedicine(rating)}
    fun getRatingCommentPost(idPost: Int) : LiveData<List<Rating>>{ return ratingRepository.getTop3RatingCommentPost(idPost)}
    fun addRatingPost(rating: Rating) {return ratingRepository.addRatingPost(rating)}
    fun addRatingDoctor(rating: Rating) {return ratingRepository.addRatingDoctor(rating)}
    fun deleteRating(rating: Rating) {return ratingRepository.deleteRating(rating)}
    fun getRating(idUser: Int, idDoctor: Int) : LiveData<Rating> { return ratingRepository.getRating(idUser, idDoctor)}
}