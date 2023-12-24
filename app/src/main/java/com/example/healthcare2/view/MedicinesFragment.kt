package com.example.healthcare2.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.adapter.MedicinesAdapter
import com.example.healthcare2.data.model.Medicine
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentMedicinesBinding
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.MedicineViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class MedicinesFragment : Fragment() {
    private lateinit var binding: FragmentMedicinesBinding
    private lateinit var medicineViewModel: MedicineViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var listMedicine: List<Medicine>
    private lateinit var medicinesAdapter: MedicinesAdapter
    private lateinit var userViewModel: UserViewModel
    private lateinit var doctorViewModel: DoctorViewModel

    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMedicinesBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)

        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
        recyclerView = binding.recyclerViewMedicines
        getMedicineList()
        getDoctor()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAddMedicine.setOnClickListener {
            findNavController().navigate(R.id.action_medicinesFragment_to_addMedicinesFragment)
        }
        binding.btnSearch.setOnClickListener {
            searchMedicine()
        }
    }
    fun getDoctor(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            doctorViewModel.getDoctor(user.idUser).observe(viewLifecycleOwner) { doctor ->
                if (doctor.idDoctor != 0){
                    binding.btnAddMedicine.visibility = View.VISIBLE
                }
            }


        }
    }
    fun searchMedicine(){
        var name: String = binding.editText.text.toString().trim()
        medicineViewModel.searchMedicine(name).observe(viewLifecycleOwner) { listMedicineResponse ->
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            listMedicine = listMedicineResponse
            medicinesAdapter.notifyDataSetChanged()

        }
    }
    fun getMedicineList() {
        medicineViewModel.getListMedicine().observe(viewLifecycleOwner) { listMedicineResponse ->
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            listMedicine = listMedicineResponse
            medicinesAdapter = MedicinesAdapter(requireContext(), listMedicine, R.layout.item_medicines)
            val navController = Navigation.findNavController(requireView())
            medicinesAdapter.setNavController(navController)
            recyclerView.adapter = medicinesAdapter

        }
    }
}