package com.example.healthcare2.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.healthcare2.R
import com.example.healthcare2.data.model.Doctor
import com.example.healthcare2.data.model.Medicine
import com.example.healthcare2.data.model.Post
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentAddMedicinesBinding
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.MedicineViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddMedicinesFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var binding: FragmentAddMedicinesBinding
    private lateinit var medicineViewModel: MedicineViewModel
    private lateinit var doctorViewModel: DoctorViewModel
    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    private lateinit var doctor: Doctor
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddMedicinesBinding.inflate(layoutInflater)
        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        getUser()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.cardViewMedcines.setOnClickListener {
            openGallery()
        }
        binding.buttonAddMedicines.setOnClickListener {
            addMedicine()
        }
    }
    fun addMedicine(){
        if (imageUri == null) {
            Toast.makeText(requireContext(), "Image is empty!", Toast.LENGTH_SHORT).show()
        } else if (binding.txtNameMedicines.text.toString().trim() == ""){
            Toast.makeText(requireContext(), "Name medicine is empty!", Toast.LENGTH_SHORT).show()
        } else if (binding.txtdescription.text.toString().trim() == ""){
            Toast.makeText(requireContext(), "Description is empty!", Toast.LENGTH_SHORT).show()
        } else if (binding.txtPriceMedicines.text.toString().trim() == ""){
            Toast.makeText(requireContext(), "Price is empty!", Toast.LENGTH_SHORT).show()
        } else {
            var nameMedicine: String = binding.txtNameMedicines.text.toString().trim()
            var description: String = binding.txtdescription.text.toString().trim()
            val priceString = binding.txtPriceMedicines.text.toString().trim()
            val price: Float = priceString.toFloatOrNull() ?: 0.0f
            var img:String = convertImageToBase64(imageUri!!)
            var medicine: Medicine = Medicine(doctor.idDoctor, nameMedicine, img, price, description)
            medicineViewModel.addMedicine(medicine)
            medicineViewModel.getAddMedicineResult().observe(viewLifecycleOwner){ result ->
                if (result == "success"){
                    Toast.makeText(requireContext(), "Add medicine successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Add medicine fail", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }
    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        return if (bytes != null) {
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } else {
            ""
        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            doctorViewModel.getDoctor(user.idUser).observe(viewLifecycleOwner) {doctorResponse ->
                doctor = doctorResponse
            }
        }
    }
    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Glide.with(requireContext())
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.selectedImage)

            binding.selectedImage.visibility = View.VISIBLE
            binding.btnAddImage.visibility = View.GONE

        }
    }
}