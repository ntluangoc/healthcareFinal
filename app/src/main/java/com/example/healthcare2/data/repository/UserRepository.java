package com.example.healthcare2.data.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.healthcare2.data.api.RestApiService;
import com.example.healthcare2.data.api.RetrofitInstance;
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

public class UserRepository {
    private Application application;

    private List<User> userList = new ArrayList<>();
    private MutableLiveData<List<User>> mutableLiveData = new MutableLiveData<>();
    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public UserRepository(Application application) {
        this.application = application;
    }
    public MutableLiveData<List<User>> getMutiableLiveData(){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<List<User>> call = apiService.getUserList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null){
                    userList = response.body();
                    mutableLiveData.setValue(userList);
                    Log.d("SUCCESS", userList.toString());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("ERROR", "msg: " + t.getMessage());
                System.out.println("Lỗi: " + t.getMessage());
            }
        });
        return mutableLiveData;
    }
    public MutableLiveData<User> getUser(String email){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<User> call = apiService.getUser(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    userMutableLiveData.setValue(response.body());
                    Log.d("SUCCESS", "User: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
        return userMutableLiveData;
    }
    public interface UserCallback {
        void onUserAddedSuccessfully(String status);
        void onUserAddFailed(String error);
    }
    public void addUser(User user){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.addUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Log.d("SUCCESS", "Add user: " + response.body().toString());
                } else {
                    Log.d("ERROR", "Add user error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("ERROR", "msg add user: " + t.getMessage());
                System.out.println("Lỗi: " + t.getMessage());
            }
        });
    }
    public void updateUser(User user, UserCallback callback){
        RestApiService apiService = RetrofitInstance.getApiService();
        Call<ResponseBody> call = apiService.updateUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String responseString = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseString);
                        String status = jsonObject.getString("status");
                        if (status.equals("success")) {
                            callback.onUserAddedSuccessfully(status);
                        } else {
                            callback.onUserAddFailed("Failed to update user");
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                        callback.onUserAddFailed(e.getMessage());
                    }
                } else {
                    callback.onUserAddFailed("update user error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onUserAddFailed("msg update user: " + t.getMessage());
            }
        });
    }
}
