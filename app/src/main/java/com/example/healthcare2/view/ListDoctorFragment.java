package com.example.healthcare2.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare2.R;
import com.example.healthcare2.adapter.DoctorAdapter;
import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.databinding.FragmentListDoctorBinding;
import com.example.healthcare2.viewmodel.DoctorViewModel;
import com.example.healthcare2.viewmodel.PostViewModel;

import java.util.ArrayList;
import java.util.List;

public class ListDoctorFragment extends Fragment {
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private List<Integer> listTotalPost;
    private FragmentListDoctorBinding binding;
    private DoctorViewModel doctorViewModel;
    private PostViewModel postViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListDoctorBinding.inflate(inflater);
//        View view = inflater.inflate(R.layout.fragment_list_doctor, container, false);
        doctorViewModel = new ViewModelProvider(this).get(DoctorViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        //Khởi tạo RecyclerView và Adapter
        recyclerView = binding.recyclerViewListDoctor;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        getListNumPostDoctor();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });
        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDoctor();
            }
        });
    }

    private void getListNumPostDoctor(){
        postViewModel.getListNumPostDoctor().observe(getViewLifecycleOwner(), new Observer<List<Integer>>() {
            @Override
            public void onChanged(List<Integer> integers) {
                listTotalPost = integers;
                getListDoctor();
            }
        });
    }
    private void getListDoctor(){
        doctorViewModel.getListDoctor().observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                doctorList = doctors;
                doctorAdapter = new DoctorAdapter(requireContext(), doctorList, listTotalPost, R.layout.item_doctor);
                NavController navController = Navigation.findNavController(requireView());
                doctorAdapter.setNavController(navController);
                // Gán Adapter cho RecyclerView
                recyclerView.setAdapter(doctorAdapter);
            }
        });
    }
    private void searchDoctor(){
        String name = binding.editText.getText().toString().trim();
        doctorViewModel.searchDoctor(name).observe(getViewLifecycleOwner(), new Observer<List<Doctor>>() {
            @Override
            public void onChanged(List<Doctor> doctors) {
                doctorList = doctors;
               doctorAdapter.notifyDataSetChanged();
            }
        });
    }
}