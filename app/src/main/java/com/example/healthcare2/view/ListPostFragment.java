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
import com.example.healthcare2.adapter.PostHomeAdapter;
import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.data.model.Post;
import com.example.healthcare2.databinding.FragmentListPostBinding;
import com.example.healthcare2.viewmodel.PostViewModel;

import java.util.List;

public class ListPostFragment extends Fragment {
    private RecyclerView recyclerView;
    private PostHomeAdapter postHomeAdapter;
    private List<Post> postList;
    private FragmentListPostBinding binding;
    private PostViewModel postViewModel;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListPostBinding.inflate(inflater);
//        View view = inflater.inflate(R.layout.fragment_list_doctor, container, false);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        //Khởi tạo RecyclerView và Adapter
        recyclerView = binding.recyclerViewListPost;
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        getListPost();
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
        binding.icAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_listPostFragment_to_addPostFragment);
            }
        });
        binding.icSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPost();
            }
        });
    }
    private void getListPost(){
        postViewModel.getListPost().observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postList = posts;
                postHomeAdapter = new PostHomeAdapter(requireContext(), postList, R.layout.item_post_home);
                NavController navController = Navigation.findNavController(requireView());
                postHomeAdapter.setNavController(navController);
                // Gán Adapter cho RecyclerView
                recyclerView.setAdapter(postHomeAdapter);
            }
        });
    }
    private void searchPost(){
        String name = binding.editText.getText().toString().trim();
        postViewModel.searchPost(name).observe(getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(List<Post> posts) {
                postList = posts;
                postHomeAdapter.notifyDataSetChanged();
            }

        });
    }

}
