package com.example.healthcare2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.adapter.CommentAdapter
import com.example.healthcare2.data.model.Comment
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.databinding.FragmentListCommentBinding
import com.example.healthcare2.viewmodel.CommentViewModel
import com.example.healthcare2.viewmodel.MedicineViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.RatingViewModel
import com.example.healthcare2.viewmodel.UserViewModel

class ListCommentFrament() : Fragment() {
    private lateinit var binding: FragmentListCommentBinding
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var ratingViewModel: RatingViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var recyclerView: RecyclerView
    private var listComment: List<Comment> = ArrayList<Comment>()
    private var listRating: List<Rating> = ArrayList<Rating>()
    private var idMedicine: Int? = null
    private var idPost: Int? = null

    constructor(idMedicine: Int?, idPost: Int?) : this() {
        this.idMedicine = idMedicine
        this.idPost = idPost
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListCommentBinding.inflate(layoutInflater)
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        recyclerView = binding.recyclerviewComment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        idMedicine = arguments?.getInt("idMedicine", 0) ?: 0
        idPost = arguments?.getInt("idPost", 0) ?: 0
        if (idMedicine != 0){
            getRatingCommentMedicine(idMedicine!!)
        } else if (idPost != 0){
            getRatingCommentPost(idPost!!)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
    fun getCommentMedicine(idMedicine: Int){
        commentViewModel.getCommentMedicine(idMedicine).observe(viewLifecycleOwner){ comments ->
            listComment = comments
            commentAdapter = CommentAdapter(requireContext(), listComment, listRating, R.layout.item_comment)
            recyclerView.adapter = commentAdapter
            binding.lottiteComment.visibility = View.GONE
            binding.recyclerviewComment.visibility = View.VISIBLE
        }
    }
    fun getCommentPost(idPost: Int){
        commentViewModel.getCommentPost(idPost).observe(viewLifecycleOwner){ comments ->
            listComment = comments
            commentAdapter = CommentAdapter(requireContext(), listComment, listRating, R.layout.item_comment)
            recyclerView.adapter = commentAdapter
            binding.lottiteComment.visibility = View.GONE
            binding.recyclerviewComment.visibility = View.VISIBLE
        }
    }
    fun getRatingCommentMedicine(idMedicine: Int){
        ratingViewModel.getRatingCommentMedicine(idMedicine).observe(viewLifecycleOwner){ ratings ->
            listRating = ratings
            getCommentMedicine(idMedicine)
        }
    }
    fun getRatingCommentPost(idPost: Int){
        ratingViewModel.getRatingCommentPost(idPost).observe(viewLifecycleOwner){ ratings ->
            listRating = ratings
//            Log.d("RATING", "list rating comment post: ${listRating.toString()}")
            getCommentPost(idPost)
        }
    }
}