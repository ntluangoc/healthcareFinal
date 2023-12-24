package com.example.healthcare2.view

import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.data.model.Like
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentDetailPostBinding
import com.example.healthcare2.utils.SharedViewModel
import com.example.healthcare2.viewmodel.LikeViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetailPostFragment : Fragment() {
    private lateinit var binding: FragmentDetailPostBinding
    private lateinit var postViewModel: PostViewModel
    private lateinit var likeViewModel: LikeViewModel
    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    val sharedViewModel : SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailPostBinding.inflate(layoutInflater)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        likeViewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val idPost = arguments?.getInt("idPost", 0) ?: 0
        getPost(idPost)
        getComment(idPost)
        addLikePost(idPost)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
        }
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }

    }
    fun addLikePost(idPost: Int){
        binding.checkboxLove.setOnClickListener {
            var like: Like = Like(user.idUser,idPost, 0)
            if (binding.checkboxLove.isChecked){
                likeViewModel.addLike(like)
            } else {
                likeViewModel.deleteLike(like)
            }
            Handler().postDelayed({
                postViewModel.getPost(idPost).observe(viewLifecycleOwner) { post ->
                    binding.txtLikePost.text = post.like.toString()
                }
            }, 1000)

        }
    }
    fun getPost(idPost: Int){
        postViewModel.getPost(idPost).observe(viewLifecycleOwner) { post ->
            Glide.with(requireContext()).load(post.img).into(binding.imgPost)
            binding.txtTitlePost.text = post.title
            sharedViewModel.rating.observe(viewLifecycleOwner, Observer { value ->
                setRatingMedicine(value)
            })
            binding.txtRatingPost.text = post.rating.toString()
            binding.txtLikePost.text = post.like.toString()
            binding.txtContentPost.text = Html.fromHtml(post.content)
            //set User
            if (!post.user.avatar.isNullOrEmpty()){
                Glide.with(requireContext()).load(post.user.avatar).into(binding.imgAvatar)
            }
            binding.txtNameUser.text = post.user.nameUser
            postViewModel.getNumPostUser(post.user.idUser).observe(viewLifecycleOwner){ numPost ->
                binding.txtSumPost.text = numPost.toString()
            }
        }
    }
    fun getComment(idPost: Int){
        val fragmentManager = childFragmentManager
        val commentFragment = CommentFragment(null, idPost)
        fragmentManager.beginTransaction().replace(R.id.fragmentCommentPost, commentFragment).commit()
    }
    fun setRatingMedicine(rating: Float){
        binding.txtRatingPost.text = rating.toString()

        if (rating>=0 && rating <=0.5){

        } else if (0.5 < rating && rating < 1){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_half)
        } else if (1 <= rating && rating < 1.5){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
        } else if (1.5<= rating && rating < 2){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_half)
        } else if (2 <= rating && rating < 2.5){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
        } else if (2.5 <= rating && rating < 3){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_half)
        } else if (3 <= rating && rating < 3.5){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_full)
        } else if (3.5 <= rating && rating < 4){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle4.setImageResource(R.drawable.ic_star_half)
        } else if (4 <= rating && rating < 4.5){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle4.setImageResource(R.drawable.ic_star_full)
        } else if (4.5 <= rating && rating < 5){
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle4.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle5.setImageResource(R.drawable.ic_star_half)
        } else {
            binding.icStarTitle1.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle2.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle3.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle4.setImageResource(R.drawable.ic_star_full)
            binding.icStarTitle5.setImageResource(R.drawable.ic_star_full)
        }
    }
}