package com.example.healthcare2.view

import com.example.healthcare2.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.adapter.PostHomeAdapter
import com.example.healthcare2.adapter.WorkAdapter
import com.example.healthcare2.data.model.Post
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentHomeBinding
import com.example.healthcare2.dialog.DialogSignupToBeADoctor
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.example.healthcare2.viewmodel.WorkViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var listPostHome : List<Post>
    private lateinit var postAdapter : PostHomeAdapter
    private lateinit var postViewModel: PostViewModel
    private lateinit var workViewModel: WorkViewModel

    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        workViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        // Khởi tạo RecyclerView và adapter
        recyclerView = binding.recyclerviewListposthome
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        getTop3Post()
        getUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.cardMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_medicinesFragment)
        }
        binding.cardDoctor.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listDoctorFragment)
        }
        binding.cardPost.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listPostFragment)
        }
        binding.txtShowMore.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_listPostFragment)

        }
    }
    fun getNextWork(){
        workViewModel.getNextWork(user.idUser).observe(viewLifecycleOwner) {nextWork ->
            if (!nextWork.isEmpty()){
                val workAdapter: WorkAdapter = WorkAdapter(requireContext(), nextWork, R.layout.item_work, workViewModel)
                val recycleViewNextWork: RecyclerView = binding.recycleViewNextWork
                recycleViewNextWork.layoutManager = LinearLayoutManager(requireContext())
                recycleViewNextWork.adapter = workAdapter
                binding.recycleViewNextWork.visibility = View.VISIBLE
                binding.txtNotAnyWork.visibility = View.GONE
            } else {
                binding.recycleViewNextWork.visibility = View.GONE
                binding.txtNotAnyWork.visibility = View.VISIBLE
            }
        }
    }
    fun getTop3Post(){
        postViewModel.getTop3Post().observe(viewLifecycleOwner) {listPostResponse ->
            listPostHome = listPostResponse
            postAdapter = PostHomeAdapter(requireContext(), listPostHome, R.layout.item_post_home)
            // Gán adapter cho RecyclerView
            recyclerView.setAdapter(postAdapter)
        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            getNextWork()
        }
    }
}