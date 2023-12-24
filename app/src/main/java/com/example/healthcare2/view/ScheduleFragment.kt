package com.example.healthcare2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare2.R
import com.example.healthcare2.adapter.WorkAdapter
import com.example.healthcare2.data.model.User
import com.example.healthcare2.data.model.Work
import com.example.healthcare2.databinding.FragmentScheduleBinding
import com.example.healthcare2.viewmodel.UserViewModel
import com.example.healthcare2.viewmodel.WorkViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ScheduleFragment : Fragment(){
    private lateinit var binding : FragmentScheduleBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var workList : List<Work>
    private lateinit var workAdapter : WorkAdapter
    private lateinit var workViewModel: WorkViewModel
    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(layoutInflater)
        recyclerView = binding.recyclerViewListWork
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            getListWorkToday()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.simpleCalendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->
            val formattedMonth = (month + 1).toString().padStart(2, '0')
            val formattedDay = dayOfMonth.toString().padStart(2, '0')
            val dateStr = "$year-$formattedMonth-$formattedDay"
            Log.d("DATE_STRING", dateStr)
            getListWorkByDate(user.idUser,dateStr)
        }
        binding.icAddWork.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_addWorkFragment)
        }
    }
    fun getListWorkToday(){
        workViewModel = ViewModelProvider(this).get(WorkViewModel::class.java)
        workViewModel.getListWorkToday(user.idUser).observe(viewLifecycleOwner) { workListByDate ->
                workList = workListByDate
                workAdapter = WorkAdapter(requireContext(), workList, R.layout.item_work, workViewModel)
                recyclerView.adapter = workAdapter
        }

    }
    fun getListWorkByDate(idUser: Int,dateString : String){
        workViewModel.getListWorkByDate(idUser,dateString).observe(viewLifecycleOwner) { workListByDate ->
                workList = workListByDate
                workAdapter = WorkAdapter(requireContext(), workList, R.layout.item_work, workViewModel)
                recyclerView.adapter = workAdapter
        }

    }
}