package com.example.healthcare2.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.healthcare2.databinding.CustomDialogLottieBinding

class DialogSendEmailFragment : DialogFragment() {
    private var _binding : CustomDialogLottieBinding? = null

    private val binding get() = _binding!!
    companion object {
        const val TAG = "DialogSendEmailFragment"
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CustomDialogLottieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}