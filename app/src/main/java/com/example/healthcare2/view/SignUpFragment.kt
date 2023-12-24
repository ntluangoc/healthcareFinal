package com.example.healthcare2.view

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.DialogLoadingBinding
import com.example.healthcare2.databinding.FragmentSignUpBinding
import com.example.healthcare2.dialog.LoadingFragment
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var loadingFragment : LoadingFragment
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        loadingFragment = LoadingFragment()
        binding.btnSignin.setOnClickListener {
            requireView().findNavController().navigateUp()
        }
        binding.textInputConfirmPassword.doOnTextChanged { text, start, before, count ->
            if (isPasswordsMatching() || binding.textInputConfirmPassword.text.isNullOrEmpty()){
                binding.edtConfirmPassword.helperText = ""
            } else {
                binding.edtConfirmPassword.helperText = "No matching password!"
            }
        }
        signUp()
    }
    private fun signUp(){
        binding.btnSignup.setOnClickListener {
            if (checkInputEmpty()){
                Toast.makeText(requireContext(), "Please complete all information", Toast.LENGTH_SHORT).show()
            } else{
                if (!binding.checkbox.isChecked){
                    Toast.makeText(requireContext(), "Please accept term & conditions", Toast.LENGTH_SHORT).show()
                } else{
                    loadingFragment.show(childFragmentManager, LoadingFragment.TAG)
                    Handler().postDelayed({
                        checkEmailExisted()
                    }, 2000) // Chạy hàm checkEmailExisted() sau 2 giây
                }
            }
//            var dialog : LottieDialog = LottieDialog(requireContext())
//                .setAnimation("loading.json")
//                .setAnimationRepeatCount(LottieDialog.INFINITE)
//                .setAutoPlayAnimation(true)
//                .setMessage("Create account successfully")
//                .setMessageColor(Color.GREEN)
//                .setDialogBackground(Color.WHITE)
//                .setCancelable(false)
//            dialog.playAnimation()
//            dialog.show()
        }

    }
    private fun checkInputEmpty() : Boolean{
        if (binding.textInputFullname.text.isNullOrEmpty() || binding.textInputEmail.text.isNullOrEmpty() || binding.textInputPassword.text.isNullOrEmpty() || binding.textInputConfirmPassword.text.isNullOrEmpty()){
            return true
        }
        return false

    }
    private fun checkEmailExisted(){
        auth.fetchSignInMethodsForEmail(binding.textInputEmail.text.toString())
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result?.signInMethods?.isEmpty()==true){
                        //email is not exist
                        createAccount()
                    } else{
                        //email is exist
                        loadingFragment.showErrorMessage("Email has existed!")
//                        Toast.makeText(requireContext(), "Email has existed!", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    loadingFragment.showErrorMessage(task.exception.toString())
                    val exception = task.exception
                    Log.d("Error", exception.toString())
                }
            }
    }
    private fun createAccount(){
        var email = binding.textInputEmail.text.toString().trim()
        var password = binding.textInputPassword.text.toString().trim()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Success", "createUserWithEmail:success")
                    addUserToDatabase(binding.textInputFullname.text.toString().trim(), email)
                    loadingFragment.showSuccessMessage("Create account successfully.", "Sign In")
//                    Toast.makeText(requireContext(), "Create account successfully!", Toast.LENGTH_SHORT).show()
                    // Đăng xuất người dùng sau khi tạo tài khoản (ngăn đăng nhập tự động)
                    auth.signOut()
//                    requireView().findNavController().navigateUp()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Error", "createUserWithEmail:failure", task.exception)
                    loadingFragment.showErrorMessage(task.exception.toString())
//                    Toast.makeText(
//                        requireContext(),
//                        "Authentication failed.",
//                        Toast.LENGTH_SHORT,
//                    ).show()

                }
            }
    }
    private fun addUserToDatabase(nameUser:  String, email: String){
        var user: User = User(nameUser, email)
        Log.w("USER", "User: ${user.toString()}")
        userViewModel.addUser(user)
    }
    private fun isPasswordsMatching(): Boolean {
        val password = binding.textInputPassword.text.toString()
        val confirmPassword = binding.textInputConfirmPassword.text.toString()
        return password == confirmPassword
    }
}

