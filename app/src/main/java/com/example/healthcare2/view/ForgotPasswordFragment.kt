package com.example.healthcare2.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import com.example.healthcare2.databinding.FragmentForgotPasswordBinding
import com.example.healthcare2.dialog.DialogSendEmailFragment

class ForgotPasswordFragment : Fragment() {
    private var binding : FragmentForgotPasswordBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnReturn?.setOnClickListener{
            it.findNavController().navigateUp()
        }
        binding?.btnSend?.setOnClickListener{
            showDialog()
        }
    }
    private fun showDialog(){
        DialogSendEmailFragment().show(childFragmentManager, DialogSendEmailFragment.TAG)
    }
}