package com.example.healthcare2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.healthcare2.data.model.Doctor
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.DialogSignupDoctorBinding
import com.example.healthcare2.viewmodel.DoctorViewModel

class DialogSignupToBeADoctor : DialogFragment() {
    private lateinit var binding: DialogSignupDoctorBinding
    private lateinit var doctorViewModel: DoctorViewModel
    private lateinit var user: User
    companion object {
        const val TAG = "DialogSignupToBeADoctor"
    }
    fun setUser(user: User) {
        this.user = user
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogSignupDoctorBinding.inflate(layoutInflater)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnSignupToBeADoctor.setOnClickListener {
            addDoctor(user)
        }
        return binding.root
    }
    fun addDoctor(user: User){
        var description: String = binding.edtDescription.text.toString().trim()
        var doctor: Doctor = Doctor(user,description)
        doctorViewModel.addDoctor(doctor)
        doctorViewModel.addDoctorResult.observe(viewLifecycleOwner) {result ->
            if (result == "success"){
                Toast.makeText(requireContext(), "Sign up to be a doctor successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Sign up to be a doctor fail", Toast.LENGTH_SHORT).show()

            }
        }
        dismiss()
    }
}