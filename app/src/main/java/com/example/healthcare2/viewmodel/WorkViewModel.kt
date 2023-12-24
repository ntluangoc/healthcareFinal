package com.example.healthcare2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.model.Work
import com.example.healthcare2.data.repository.WorkRepository

class WorkViewModel(application: Application) : AndroidViewModel(application) {
    private val workRepository : WorkRepository = WorkRepository(application)
    private val addWorkResult: MutableLiveData<String> = MutableLiveData<String>()
    fun getAddWorkResult(): LiveData<String> {
        return addWorkResult
    }
    fun getListWorkToday(idUser: Int): LiveData<List<Work>> {
        return workRepository.getListWorkToday(idUser)
    }

    fun getListWorkByDate(idUser: Int,date: String): LiveData<List<Work>> {
        return workRepository.getListWorkByDate(idUser,date)
    }

    fun updateIsCheckWork(work: Work) {
        workRepository.updateIsCheckWork(work)
    }
    fun addWork(work: Work){
        workRepository.addWork(work, object : WorkRepository.WorkCallback{
            override fun onWorkAddedSuccessfully(status: String) {
                addWorkResult.value = status
            }

            override fun onWorkAddFailed(error: String) {
                addWorkResult.value = error
            }

        })
    }
    fun getNextWork(idUser: Int) : LiveData<List<Work>> {
        return workRepository.getNextWork(idUser)
    }
}