package com.example.healthcare2.view

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.healthcare2.data.model.Doctor
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentEditUserBinding
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditUserFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: FragmentEditUserBinding

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
        binding = FragmentEditUserBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        getUser()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCancel.setOnClickListener { findNavController().navigateUp() }
        binding.avatarUser.setOnClickListener {
            openGallery()
        }
        binding.btnTime.setOnClickListener { chooseTime() }
        binding.buttonSave.setOnClickListener {
            updateUser()
        }
    }
    fun updateUser(){

        val name:String = binding.txtEditName.text.toString().trim()
        val birthday:String = binding.txtEditBirthday.text.toString().trim()
        val email:String = binding.txtEditEmail.text.toString().trim()
        if (imageUri != null){
            var img:String = convertImageToBase64(imageUri!!)
            user.avatar = img
        } else {
            user.avatar = null
        }
        user.nameUser = name
        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: java.util.Date = format.parse(birthday)
        user.birhtday = date
        user.email = email
        userViewModel.updateUser(user)
        Log.d("USER", "User: $user")
        userViewModel.addUserResult.observe(viewLifecycleOwner){ result ->
            if (result == "success"){
                if (doctor != null){
                    doctor.description = binding.txtEditDesciption.text.toString().trim()
                    doctorViewModel.updateDoctor(doctor)
                    doctorViewModel.addDoctorResult.observe(viewLifecycleOwner) { result ->
                        if (result == "success"){
                            Toast.makeText(requireContext(), "Update user successfully", Toast.LENGTH_SHORT).show()
                            findNavController().navigateUp()
                        } else {
                            Toast.makeText(requireContext(), "Update user fail", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "Update user successfully", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(requireContext(), "Update user fail", Toast.LENGTH_SHORT).show()

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
    fun chooseTime(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, yearSelected, monthOfYear, dayOfMonth ->
                // Hiển thị hoặc làm gì đó với selectedDateTime
                val time: String = String.format(
                    Locale.getDefault(),
                    "%04d-%02d-%02d",
                    yearSelected,
                    monthOfYear + 1,
                    dayOfMonth
                )
                binding.txtEditBirthday.text = time
            },
            year,
            month,
            day
        )
        datePickerDialog.show()

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
                .into(binding.avatarUser)

        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            if (!user.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(user.avatar).into(binding.avatarUser)
            val name: String? = user.nameUser
            val editableName: Editable = Editable.Factory.getInstance().newEditable(name)
            binding.txtEditName.text = editableName
            if (user.birhtday != null){
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(user.birhtday)
                binding.txtEditBirthday.text = formattedDate
            }
            val email: String? = user.email
            val editableEmail: Editable = Editable.Factory.getInstance().newEditable(email)
            binding.txtEditEmail.text = editableEmail
            getDoctor()


        }
    }
    fun getDoctor(){
        doctorViewModel.getDoctor(user.idUser).observe(viewLifecycleOwner) { doctorResponse ->
            if (doctorResponse.idDoctor != 0){
                doctor = doctorResponse
                val description: String? = doctor.description
                val editableDescription: Editable = Editable.Factory.getInstance().newEditable(description)
                binding.txtEditDesciption.text = editableDescription
                binding.linearLayoutDescription.visibility = View.VISIBLE
            }
        }
    }
}