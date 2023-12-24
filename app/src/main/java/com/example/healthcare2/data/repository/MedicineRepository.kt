package com.example.healthcare2.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.healthcare2.data.api.RetrofitInstance
import com.example.healthcare2.data.model.Medicine
import okhttp3.ResponseBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MedicineRepository (application: Application){
    private var medicineMutableLiveData = MutableLiveData<Medicine>()
    private var listMedicineMutableLiveData = MutableLiveData<List<Medicine>>()

    fun getMedicine(idMedicine:Int) : MutableLiveData<Medicine>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<Medicine> = apiService.getMedicine(idMedicine)
        call.enqueue(object : Callback<Medicine?> {
            override fun onResponse(call: Call<Medicine?>, response: Response<Medicine?>) {
                if (response.body() != null) {
                    val medicine : Medicine = response.body()!!
                    medicineMutableLiveData.setValue(medicine)
                    Log.d("SUCCESS", medicine.toString())
                }
            }

            override fun onFailure(call: Call<Medicine?>, t: Throwable) {
                Log.d("ERROR", "msg: " + t.message)
                println("Lỗi: " + t.message)
            }
        })
        return medicineMutableLiveData
    }
    fun getListMedicine() : MutableLiveData<List<Medicine>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Medicine>> = apiService.medicineList
        call.enqueue(object : Callback<List<Medicine>> {
            override fun onResponse(
                call: Call<List<Medicine>>,
                response: Response<List<Medicine>>
            ) {
                if (response.isSuccessful){
                    listMedicineMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list medicine successfully")
                }
            }

            override fun onFailure(call: Call<List<Medicine>>, t: Throwable) {
                Log.d("ERROR", "msg get list medicine: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listMedicineMutableLiveData
    }
    fun searchMedicine(name: String) : MutableLiveData<List<Medicine>>{
        val apiService = RetrofitInstance.getApiService()
        val call : Call<List<Medicine>> = apiService.searchMedicine(name)
        call.enqueue(object : Callback<List<Medicine>> {
            override fun onResponse(
                call: Call<List<Medicine>>,
                response: Response<List<Medicine>>
            ) {
                if (response.isSuccessful){
                    listMedicineMutableLiveData.value = response.body()
                    Log.d("SUCCESS", "Get list medicine successfully")
                }
            }

            override fun onFailure(call: Call<List<Medicine>>, t: Throwable) {
                Log.d("ERROR", "msg get list medicine: " + t.message)
                println("Lỗi: " + t.message)
            }

        })
        return listMedicineMutableLiveData
    }
    interface MedicineCallback {
        fun onMedicineAddedSuccessfully(status: String)
        fun onMedicineAddFailed(error: String)
    }
    fun addMedicine(medicine: Medicine, callback: MedicineCallback){
        val apiService = RetrofitInstance.getApiService()
        val call = apiService.addMedicine(medicine)

        call.enqueue(object : Callback<ResponseBody> {
            // Xử lý kết quả ở đây
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    try {
                        val responseString = response.body()!!.string()
                        val jsonObject = JSONObject(responseString)
                        val status = jsonObject.getString("status")
                        if (status == "success") {
                            callback.onMedicineAddedSuccessfully(status)
                        } else {
                            callback.onMedicineAddFailed("Failed to add doctor")
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        callback.onMedicineAddFailed(e.message.toString())
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        callback.onMedicineAddFailed(e.message.toString())
                    }
                } else {
                    callback.onMedicineAddFailed("Add doctor error: " + response.message())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
}