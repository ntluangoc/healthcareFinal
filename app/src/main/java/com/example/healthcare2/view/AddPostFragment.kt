package com.example.healthcare2.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.healthcare2.R
import com.example.healthcare2.data.model.Post
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentAddPostBinding
import com.example.healthcare2.dialog.DialogSignupToBeADoctor
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddPostFragment : Fragment() {
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var binding: FragmentAddPostBinding
    private lateinit var postViewModel: PostViewModel

    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(layoutInflater)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        getUser()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.cardViewPost.setOnClickListener {
            openGallery()
        }
        binding.buttonAddPost.setOnClickListener {
            addPost()
        }
    }
    fun addPost(){
        if (imageUri == null) {
            Toast.makeText(requireContext(), "Image is empty!", Toast.LENGTH_SHORT).show()
        } else if (binding.txtTitlePost.text.toString().trim() == ""){
            Toast.makeText(requireContext(), "Title is empty!", Toast.LENGTH_SHORT).show()
        } else if (binding.txtContent.text.toString().trim() == ""){
            Toast.makeText(requireContext(), "Content is empty!", Toast.LENGTH_SHORT).show()
        } else {
            var title: String = binding.txtTitlePost.text.toString().trim()
            var content: String = binding.txtContent.text.toString().trim()
            var img:String = convertImageToBase64(imageUri!!)
            var post: Post = Post(user.idUser, title, img, content)
            postViewModel.addPost(post)
            postViewModel.getAddPostResult().observe(viewLifecycleOwner){ result ->
                if (result == "success"){
                    Toast.makeText(requireContext(), "Add post successfully", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                } else {
                    Toast.makeText(requireContext(), "Add post fail", Toast.LENGTH_SHORT).show()

                }
            }

        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
        }
    }
    private fun convertImageToBase64(uri: Uri): String {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        return if (bytes != null) {
            Base64.encodeToString(bytes, Base64.DEFAULT)
        } else {
            ""
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            Glide.with(requireContext())
                .load(imageUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(binding.selectedImage)

            binding.selectedImage.visibility = View.VISIBLE
            binding.btnAddImage.visibility = View.GONE

        }
    }
}