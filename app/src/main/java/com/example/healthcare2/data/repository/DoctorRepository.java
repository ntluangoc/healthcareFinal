package com.example.healthcare2.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.healthcare2.data.api.RestApiService;
import com.example.healthcare2.data.api.RetrofitInstance;
import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.data.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorRepository {
    private Application application;


    private MutableLiveData<Doctor> doctorMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Doctor>> listDoctorMutableLiveData = new MutableLiveData<List<Doctor>>();

    public DoctorRepository(Application application) {
        this.application = application;
    }
    public MutableLiveData<Doctor> getDoctor(int idUser){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<Doctor> call = apiService.getDoctor(idUser);
        call.enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.body() != null){
                    Doctor doctor = response.body();
                    doctorMutableLiveData.setValue(doctor);
                    Log.d("SUCCESS", doctor.toString());
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Log.d("ERROR", "msg: " + t.getMessage());
                System.out.println("Lỗi: " + t.getMessage());
            }
        });
        return doctorMutableLiveData;
    }
    public MutableLiveData<List<Doctor>> getListDoctor(){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<Doctor>> call = apiService.getListDoctor();
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()){
                    listDoctorMutableLiveData.setValue(response.body());
                    Log.d("SUCCESS", "get list doctor successfully");
                    Log.d("DOCTOR", "list doctor:" + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.d("ERROR", "msg get list doctor: " + t.getMessage());
                System.out.println("Lỗi: " + t.getMessage());
            }
        });
        return listDoctorMutableLiveData;
    }
    public interface DoctorCallback {
        void onDoctorAddedSuccessfully(String status);
        void onDoctorAddFailed(String error);
    }
    public void addDoctor(Doctor doctor, DoctorCallback callback) {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.addDoctor(doctor);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            callback.onDoctorAddedSuccessfully(status);
                        } else {
                            callback.onDoctorAddFailed("Failed to add doctor");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        callback.onDoctorAddFailed(e.getMessage());
                    }
                } else {
                    callback.onDoctorAddFailed("Add doctor error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onDoctorAddFailed("msg add doctor: " + t.getMessage());
            }
        });
    }
    public void updateDoctor(Doctor doctor, DoctorCallback callback) {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.updateDoctor(doctor);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            callback.onDoctorAddedSuccessfully(status);
                        } else {
                            callback.onDoctorAddFailed("Failed to update doctor");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        callback.onDoctorAddFailed(e.getMessage());
                    }
                } else {
                    callback.onDoctorAddFailed("update doctor error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onDoctorAddFailed("msg update doctor: " + t.getMessage());
            }
        });
    }
    public MutableLiveData<List<Doctor>> searchDoctor(String name) {
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<Doctor>> call = apiService.searchDoctor(name);
        call.enqueue(new Callback<List<Doctor>>() {
            @Override
            public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
                if (response.isSuccessful()){
                    listDoctorMutableLiveData.setValue(response.body());
                    Log.d("SUCCESS", "get list doctor successfully");
                }
            }

            @Override
            public void onFailure(Call<List<Doctor>> call, Throwable t) {
                Log.d("ERROR", "msg get list doctor: " + t.getMessage());
                System.out.println("Lỗi: " + t.getMessage());
            }
        });
        return listDoctorMutableLiveData;
    }
}
