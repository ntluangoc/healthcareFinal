package com.example.healthcare2.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.healthcare2.R
import com.example.healthcare2.adapter.IntroSlideAdapter
import com.example.healthcare2.databinding.FragmentOnboardingBinding
import com.example.healthcare2.data.dataclass.IntroSlider
import kotlinx.coroutines.launch


class OnboardingFragment : Fragment() {

    private var binding : FragmentOnboardingBinding? = null
    private val introSliderAdapter = IntroSlideAdapter(
        listOf(
            IntroSlider(
                "Health Tips / Advice",
                "Discover tips and advice to help you to help maintain transform and main your health",
                "doctor.json"
            ),
            IntroSlider(
                "Message / Review",
                "Message receiving advice from the doctor or reading review from others",
                "chatting.json"
            ),
            IntroSlider(
                "Schedule",
                "Schedule things to do during the day for better health care",
                "schedule.json"
            ),
            IntroSlider(
                "Health care",
                "Let's start taking care of your health",
                "health.json"
            )
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnboardingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set the adapter to the viewpager2

        binding?.viewPager?.adapter = introSliderAdapter
        //sets the viewpager2 to the indicator
        binding?.indicator?.setViewPager(binding?.viewPager)

        binding?.viewPager?.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    /*
                    *check if its the last page, change text on the button
                    *from next to finish and set the click listener to
                    *to navigate to welcome screen else set the text to next
                    * and click listener to move to next page
                    */
                    if (position == introSliderAdapter.itemCount - 1) {
                        //this animation is added to the finish button
                        val animation = AnimationUtils.loadAnimation(
                            requireActivity(),
                            R.anim.app_name_animation
                        )
                        //binding?.buttonNext?.animation = animation
                        binding?.buttonNext?.text = "Finish"
                        binding?.buttonNext?.setOnClickListener {
                            lifecycleScope.launch {
                                onboardingIsFinished()
                            }
                            requireView().findNavController()
                                .navigate(R.id.action_onboardingFragment_to_signInFragment)
                        }
                    } else {
                        binding?.buttonNext?.text = "Next"
                        binding?.buttonNext?.setOnClickListener {
                            binding?.viewPager?.currentItem?.let {
                                binding?.viewPager?.setCurrentItem(it + 1, false)
                            }
                        }
                    }
                }
            })
    }
    private fun onboardingIsFinished(){
        val sharePreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharePreferences.edit()
        editor.putBoolean("finished", true)
        editor.apply()
    }

}