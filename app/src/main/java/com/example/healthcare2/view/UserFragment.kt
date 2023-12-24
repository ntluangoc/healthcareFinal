package com.example.healthcare2.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentUserBinding
import com.example.healthcare2.dialog.DialogSignupToBeADoctor
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var doctorViewModel: DoctorViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        getUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_userFragment_to_signInFragment)
        }
        binding.btnInformation.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_detailUserFragment)
        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            if (!user.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(user.avatar).into(binding.imgAvatarUser)
            binding.txtNameUser.text = user.nameUser
            binding.txtEmail.text = user.email
            getDoctor()
            binding.txtSignupDoctor.setOnClickListener {
                val dialog = DialogSignupToBeADoctor()
                dialog.setUser(user)
                dialog.show(childFragmentManager, DialogSignupToBeADoctor.TAG)
            }

        }
    }
    fun getDoctor(){
        doctorViewModel.getDoctor(user.idUser).observe(viewLifecycleOwner) { doctor ->
            if (doctor.idDoctor != 0){
                binding.btnSignupToBeADoctor.visibility = View.GONE
            } else {
                binding.btnSignupToBeADoctor.setOnClickListener {

                }
            }
        }
    }
}