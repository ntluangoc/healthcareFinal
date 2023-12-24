package com.example.healthcare2.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.healthcare2.R
import com.example.healthcare2.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var binding : FragmentSplashBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler(Looper.getMainLooper()).postDelayed({
            if (onboardingIsFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_signInFragment)
            } else{
                findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
            }
        }, 5000)
        binding = FragmentSplashBinding.inflate(layoutInflater)


        //load anim
        val animFromTop = AnimationUtils.loadAnimation(binding?.root?.context, R.anim.from_top)
        val animFromBottom = AnimationUtils.loadAnimation(binding?.root?.context, R.anim.from_bottom)
//        binding?.splashImage?.animation = animFromTop
        binding?.splashText?.animation = animFromBottom
        binding?.splashImage?.playAnimation()
        return binding?.root
    }

    private fun onboardingIsFinished() : Boolean{
        val sharePreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharePreferences.getBoolean("finished", false)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null // Tránh rò rỉ bộ nhớ khi Fragment bị huỷ
    }
}