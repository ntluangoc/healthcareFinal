package com.example.healthcare2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.data.model.User;
import com.example.healthcare2.data.repository.DoctorRepository;
import com.example.healthcare2.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private DoctorRepository doctorRepository;
    private MutableLiveData<String> addUserResult = new MutableLiveData<>();
    public LiveData<String> getAddUserResult() {
        return addUserResult;
    }
    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        doctorRepository = new DoctorRepository(application);
    }
    public LiveData<List<User>> getAllUser(){
        return userRepository.getMutiableLiveData();
    }
    public LiveData<User> getUser(String email) { return userRepository.getUser(email);}
    public void addUser(User user){ userRepository.addUser(user); }
    public void updateUser(User user){
        userRepository.updateUser(user, new UserRepository.UserCallback() {

            @Override
            public void onUserAddedSuccessfully(String status) {
                addUserResult.setValue(status);
            }

            @Override
            public void onUserAddFailed(String error) {
                addUserResult.setValue(error);
            }
        });
    }
}
