package com.example.healthcare2.dialog

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.healthcare2.R
import com.example.healthcare2.databinding.DialogLoadingBinding

class LoadingFragment : DialogFragment() {
    private var _binding : DialogLoadingBinding? = null
    private val binding get() = _binding!!
    companion object {
        const val TAG = "LoadingFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogLoadingBinding.inflate(layoutInflater)
        isCancelable = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    public fun showErrorMessage(message : String){
        binding.loadingAnimation.visibility = View.GONE
        binding.linearMessage.visibility = View.VISIBLE
        binding.btnFunction.visibility = View.VISIBLE
        binding.imgIconMessage.setImageResource(R.drawable.ic_round_error)
        binding.btnFunction.text = "Cancel"
        val color = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.red))
        binding.btnFunction.backgroundTintList = color
        binding.txtMessage.text = message
        binding.btnFunction.setOnClickListener {
            dismiss()
        }
    }
    public fun showSuccessMessage(message: String, btnText: String){
        binding.loadingAnimation.visibility = View.GONE
        binding.linearMessage.visibility = View.VISIBLE
        binding.btnFunction.visibility = View.VISIBLE
        binding.txtMessage.text = message
        binding.imgIconMessage.setImageResource(R.drawable.ic_baseline_check_circle_24)
        binding.btnFunction.text = btnText
        val color = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.mainColor))
        binding.btnFunction.backgroundTintList = color
        binding.btnFunction.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}