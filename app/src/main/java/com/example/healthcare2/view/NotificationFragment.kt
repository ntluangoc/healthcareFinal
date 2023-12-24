package com.example.healthcare2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare2.R
import com.example.healthcare2.adapter.NotificationAdapter
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentNotificationBinding
import com.example.healthcare2.viewmodel.CommentViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var recycleView : RecyclerView
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(layoutInflater)
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        recycleView = binding.recycleViewlistNotification
        recycleView.layoutManager = LinearLayoutManager(requireContext())
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            getNotificationList(user.idUser)

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun getNotificationList(idUser: Int) {
        commentViewModel.getListCommentNotification(idUser).observe(viewLifecycleOwner) { notificationList ->
            val adapter = NotificationAdapter(requireContext(), notificationList, R.layout.item_notification)
            val navController = findNavController(requireView())
            adapter.setNavController(navController)
            recycleView.adapter = adapter
        }
    }
}