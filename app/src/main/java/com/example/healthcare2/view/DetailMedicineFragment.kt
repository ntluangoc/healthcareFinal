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
import com.example.healthcare2.databinding.FragmentDetailMedicineBinding
import com.example.healthcare2.utils.SharedViewModel
import com.example.healthcare2.viewmodel.LikeViewModel
import com.example.healthcare2.viewmodel.MedicineViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DetailMedicineFragment : Fragment() {
    private lateinit var binding : FragmentDetailMedicineBinding
    private lateinit var medicineViewModel: MedicineViewModel
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
    ): View {
        binding = FragmentDetailMedicineBinding.inflate(layoutInflater)
        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        likeViewModel = ViewModelProvider(this).get(LikeViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val idMedicine = arguments?.getInt("idMedicine", 0) ?: 0
        getMedicine(idMedicine)
        getComment(idMedicine)
        addLikeMedicine(idMedicine)
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
    fun getComment(idMedicine: Int){
        val fragmentManager = childFragmentManager
        val commentFragment = CommentFragment(idMedicine, null)
        fragmentManager.beginTransaction().replace(R.id.fragmentComment, commentFragment).commit()
    }
    fun getMedicine(idMedicine : Int){
        medicineViewModel.getMedicine(idMedicine).observe(viewLifecycleOwner) { medicine ->
            Glide.with(requireContext()).load(medicine.img).into(binding.imgMedicine)
            binding.txtNameMedicine.text = medicine.nameMedicine
            binding.txtPriceMedicine.text = medicine.price.toString()
            sharedViewModel.rating.observe(viewLifecycleOwner, Observer { value ->
                setRatingMedicine(value)
            })

            likeViewModel.getIsLikeMedicine(medicine.idMedicine, auth?.email.toString()).observe(viewLifecycleOwner){ isLike ->
                binding.checkboxLove.isChecked = isLike == 1
            }
            binding.txtLikeMedicine.text = medicine.like.toString()
            binding.txtContentMedicine.text = Html.fromHtml(medicine.content)
            //đặt user
            binding.txtNameUser.text = medicine.doctor.nameUser
            binding.txtRatingDoctor.text = medicine.doctor.ratingDoctor.toString()
            if (!medicine.doctor.avatar.isNullOrEmpty()){
                Glide.with(requireContext()).load(medicine.doctor.avatar).into(binding.imgAvatar)
            }
            postViewModel.getNumPostUser(medicine.doctor.idUser).observe(viewLifecycleOwner){ numPost ->
                binding.txtSumPost.text = numPost.toString()
            }
        }
    }
    fun addLikeMedicine(idMedicine: Int){
        binding.checkboxLove.setOnClickListener {
            var like: Like = Like(user.idUser,0, idMedicine)
            if (binding.checkboxLove.isChecked){
                likeViewModel.addLike(like)
            } else {
                likeViewModel.deleteLike(like)
            }
            Handler().postDelayed({
                medicineViewModel.getMedicine(idMedicine).observe(viewLifecycleOwner) { medicine ->
                    binding.txtLikeMedicine.text = medicine.like.toString()
                }
            }, 1000) // 1000ms = 1 giây
        }
    }
    fun setRatingMedicine(rating: Float){
        binding.txtRatingMedicine.text = rating.toString()

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