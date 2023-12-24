package com.example.healthcare2.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.data.repository.DoctorRepository;

import java.util.List;

public class DoctorViewModel extends AndroidViewModel {
    private DoctorRepository doctorRepository;
    public DoctorViewModel(@NonNull Application application) {
        super(application);
        doctorRepository = new DoctorRepository(application);
    }
    private MutableLiveData<String> addDoctorResult = new MutableLiveData<>();

    public LiveData<String> getAddDoctorResult() {
        return addDoctorResult;
    }
    public LiveData<Doctor> getDoctor(int idUser) {return doctorRepository.getDoctor(idUser);}
    public LiveData<List<Doctor>> getListDoctor(){
        return doctorRepository.getListDoctor();
    }
    public LiveData<List<Doctor>> searchDoctor(String name) { return doctorRepository.searchDoctor(name);}
    public void addDoctor(Doctor doctor){
        doctorRepository.addDoctor(doctor, new DoctorRepository.DoctorCallback() {
            @Override
            public void onDoctorAddedSuccessfully(String status) {
                addDoctorResult.setValue(status);
            }

            @Override
            public void onDoctorAddFailed(String error) {
                addDoctorResult.setValue(error);
            }
        });
    }
    public void updateDoctor(Doctor doctor){
        doctorRepository.updateDoctor(doctor, new DoctorRepository.DoctorCallback() {
            @Override
            public void onDoctorAddedSuccessfully(String status) {
                addDoctorResult.setValue(status);
            }

            @Override
            public void onDoctorAddFailed(String error) {
                addDoctorResult.setValue(error);
            }
        });
    }
}
