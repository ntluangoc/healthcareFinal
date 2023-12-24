package com.example.healthcare2.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.model.Medicine
import com.example.healthcare2.data.model.Post
import com.example.healthcare2.data.repository.MedicineRepository
import com.example.healthcare2.data.repository.PostRepository

class MedicineViewModel(application: Application) : AndroidViewModel(application) {
    private val medicineRepository: MedicineRepository = MedicineRepository(application)
    private val addMedicineResult: MutableLiveData<String> = MutableLiveData<String>()
    fun getAddMedicineResult(): LiveData<String> {
        return addMedicineResult
    }
    fun getMedicine(idMedicine : Int) : LiveData<Medicine>{ return medicineRepository.getMedicine(idMedicine)}
    fun getListMedicine() : LiveData<List<Medicine>> { return medicineRepository.getListMedicine()}
    fun searchMedicine(name:String) : LiveData<List<Medicine>> { return medicineRepository.searchMedicine(name)}
    fun addMedicine(medicine: Medicine) {
        medicineRepository.addMedicine(medicine, object : MedicineRepository.MedicineCallback{
            override fun onMedicineAddedSuccessfully(status: String) {
                addMedicineResult.value = status
                Log.d("SUCCESS", "Add medicine: $status")
            }

            override fun onMedicineAddFailed(error: String) {
                addMedicineResult.value = error
                Log.d("ERROR", "Add medicine: $error")
            }

        })
    }
}