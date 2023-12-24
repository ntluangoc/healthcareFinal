package com.example.healthcare2.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.healthcare2.data.model.User
import com.example.healthcare2.data.model.Work
import com.example.healthcare2.databinding.FragmentAddPostBinding
import com.example.healthcare2.databinding.FragmentAddWorkBinding
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.example.healthcare2.viewmodel.WorkViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddWorkFragment : Fragment() {
    private lateinit var binding: FragmentAddWorkBinding
    private lateinit var workViewModel: WorkViewModel
    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddWorkBinding.inflate(layoutInflater)
        workViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.icClock.setOnClickListener {
            chooseTime()
        }
        binding.buttonAddPost.setOnClickListener {
            addWork()
        }
    }
    fun addWork(){

        if (binding.txtTitlePost.text.toString().trim().isNullOrEmpty())
            Toast.makeText(requireContext(), "Title is empty!", Toast.LENGTH_SHORT).show()
        else if (binding.txtTimePost.text.toString().trim().isNullOrEmpty())
            Toast.makeText(requireContext(), "Time is empty!", Toast.LENGTH_SHORT).show()
        else {
            val title: String = binding.txtTitlePost.text.toString().trim()
            val timeString: String = binding.txtTimePost.text.toString().trim()
            val note: String = binding.txtContent.text.toString().trim()
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date: Date = format.parse(timeString)

            val timestamp = Timestamp(date.time)
            val work: Work = Work(user.idUser, title, timestamp, note)
            Log.d("WORK", "Work add: $work")
            workViewModel.addWork(work)
            workViewModel.getAddWorkResult().observe(viewLifecycleOwner){ result ->
                if (result == "success"){
                    Toast.makeText(requireContext(), "Add work successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Add work fail", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    fun chooseTime(){
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, yearSelected, monthOfYear, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    requireContext(),
                    { _, hourOfDay, minuteOfHour ->
                        // Xử lý khi người dùng chọn ngày và giờ
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(Calendar.YEAR, yearSelected)
                        selectedDateTime.set(Calendar.MONTH, monthOfYear)
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minuteOfHour)

                        // Hiển thị hoặc làm gì đó với selectedDateTime
                        val time: String = String.format(
                            Locale.getDefault(),
                            "%04d-%02d-%02d %02d:%02d",
                            yearSelected,
                            monthOfYear + 1,
                            dayOfMonth,
                            hourOfDay,
                            minuteOfHour
                        )
                        binding.txtTimePost.text = time
                    },
                    hour,
                    minute,
                    true // Hiển thị 24 giờ
                )
                timePickerDialog.show()
            },
            year,
            month,
            day
        )
        datePickerDialog.show()

    }
}