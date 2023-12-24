package com.example.healthcare2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.healthcare2.databinding.SlideItemContainerBinding
import com.example.healthcare2.data.dataclass.IntroSlider


class IntroSlideAdapter (private val introSlides : List<IntroSlider>) : RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder>(){

    var onTextPassed : ((textView:TextView) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(SlideItemContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        holder.bind(introSlides[position])
    }
    inner class IntroSlideViewHolder(private val binding : SlideItemContainerBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(introSlide: IntroSlider){
            binding.textTitle.text = introSlide.title
            binding.textDescription.text = introSlide.description
            binding.imageSlideIcon.imageAssetsFolder = "images"
            binding.imageSlideIcon.setAnimation(introSlide.icon)
            onTextPassed?.invoke(binding.textTitle)
        }
    }
}