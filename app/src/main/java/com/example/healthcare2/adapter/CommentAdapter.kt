package com.example.healthcare2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.data.model.Comment
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.databinding.ItemCommentBinding

class CommentAdapter(private val context : Context, private var listComment : List<Comment>, private var listRating : List<Rating>,private val layout: Int)
    : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    private var inflater : LayoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(comment: Comment, rating: Rating){
            if (!comment.user.avatar.isNullOrEmpty()) Glide.with(context).load(comment.user.avatar).into(binding.imgAvatar)
            binding.txtNameUser.text = comment.user.nameUser
            setRatingComment(rating.rating)
            binding.txtComment.text = comment.content
            binding.txtTimeComment.text = comment.created_at.toString()
        }
        fun setRatingComment(rating: Int){
            if (rating>=0 && rating <=0.5){

            } else if (rating == 1){
                binding.icStarComment1.setImageResource(R.drawable.ic_star_full)
            } else if (rating == 2){
                binding.icStarComment1.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment2.setImageResource(R.drawable.ic_star_full)
            } else if (rating == 3){
                binding.icStarComment1.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment2.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment3.setImageResource(R.drawable.ic_star_full)
            } else if (rating == 4){
                binding.icStarComment1.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment2.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment3.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment4.setImageResource(R.drawable.ic_star_full)
            } else {
                binding.icStarComment1.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment2.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment3.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment4.setImageResource(R.drawable.ic_star_full)
                binding.icStarComment5.setImageResource(R.drawable.ic_star_full)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCommentBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listComment.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val comment = listComment[position]
            val rating = listRating[position]
            holder.bind(comment, rating)

    }
}