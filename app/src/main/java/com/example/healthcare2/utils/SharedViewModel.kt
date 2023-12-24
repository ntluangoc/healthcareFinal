package com.example.healthcare2.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val rating: MutableLiveData<Float> = MutableLiveData()
}