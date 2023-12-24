package com.example.healthcare2.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.healthcare2.R
import com.example.healthcare2.databinding.ActivityMainBinding
import com.example.healthcare2.databinding.FragmentForgotPasswordBinding
import com.gauravk.bubblenavigation.BubbleNavigationLinearView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNav = findViewById<BubbleNavigationLinearView>(R.id.bottom_navigation_view_linear)
        val navController = findNavController(R.id.fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.signInFragment, R.id.signUpFragment, R.id.forgotPasswordFragment, R.id.onboardingFragment -> {
                    // Ẩn Bubble Navigation nếu đang trong SplashFragment hoặc SignFragment
                    bottomNav.visibility = View.GONE

                }
                else -> {
                    // Hiển thị Bubble Navigation trong các fragment khác
                    bottomNav.visibility = View.VISIBLE

                }
            }
        }
        bottomNav.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> navController.navigate(R.id.homeFragment)
                1 -> navController.navigate(R.id.scheduleFragment)
                2 -> navController.navigate(R.id.notificationFragment)
                3 -> navController.navigate(R.id.userFragment)
            }
        }
    }
}