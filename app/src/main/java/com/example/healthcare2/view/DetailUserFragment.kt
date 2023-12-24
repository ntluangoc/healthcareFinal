package com.example.healthcare2.view
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.adapter.PostUserAdapter
import com.example.healthcare2.data.model.Doctor
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentDetailUserBinding
import com.example.healthcare2.viewmodel.DoctorViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.RatingViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Locale

class DetailUserFragment : Fragment() {
    private lateinit var binding: FragmentDetailUserBinding
    private lateinit var recyclerView : RecyclerView
    private lateinit var postViewModel: PostViewModel
    private lateinit var ratingViewModel: RatingViewModel
    private lateinit var doctorViewModel: DoctorViewModel

    private lateinit var userViewModel: UserViewModel
    private val auth: FirebaseUser? = Firebase.auth.currentUser
    private lateinit var user: User
    private lateinit var doctor: Doctor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailUserBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
        doctorViewModel = ViewModelProvider(this).get(DoctorViewModel::class.java)
        recyclerView = binding.recyclerviewDetailUser
        val idUser = arguments?.getInt("idUser", 0) ?: 0
        if (idUser == 0){
            getUser()
        } else {
            getDoctorById(idUser)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnReturn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.icEditUser.setOnClickListener {
            findNavController().navigate(R.id.action_detailUserFragment_to_editUserFragment)
        }
    }
    fun setRatingDoctor(){
        var rating: Int = 0
        binding.checkboxStar5.setOnClickListener {
            setStarRating(5)
            if (binding.checkboxStar5.isChecked) {
                deleteRating()
                rating = 5
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    addRatingDoctor(rating)
                }, 1000)
            }
        }
        binding.checkboxStar4.setOnClickListener {
            setStarRating(4)
            if (binding.checkboxStar4.isChecked) {
                deleteRating()
                rating = 4
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    addRatingDoctor(rating)
                }, 1000)
            }
        }
        binding.checkboxStar3.setOnClickListener {
            setStarRating(3)
            if (binding.checkboxStar3.isChecked) {
                deleteRating()
                rating = 3
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    addRatingDoctor(rating)
                }, 1000)
            }
        }
        binding.checkboxStar2.setOnClickListener {
            setStarRating(2)
            if (binding.checkboxStar2.isChecked) {
                deleteRating()
                rating = 2
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    addRatingDoctor(rating)
                }, 1000)
            }
        }
        binding.checkboxStar1.setOnClickListener {
            setStarRating(1)
            if (binding.checkboxStar1.isChecked) {
                deleteRating()
                rating = 1
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    addRatingDoctor(rating)
                }, 1000)
            }
        }





    }
    fun addRatingDoctor(rating: Int){
        var ratingAdd: Rating = Rating(user.idUser, doctor.idDoctor, 0, 0, rating )
        ratingViewModel.addRatingDoctor(ratingAdd)
        Log.d("RATING", "Rating add: $ratingAdd")
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            // Gọi hàm refreshRatingDoctor() ở đây
            refreshRatingDoctor()
        }, 1500) // Đợi 1.5 giây trước khi thực hiện hàm refreshRatingDoctor()
    }
    fun deleteRating(){
        var ratingDelete: Rating = Rating(doctor.idUser, doctor.idDoctor, 0, 0, 0 )
        ratingViewModel.deleteRating(ratingDelete)

    }
    fun isRatingDoctor(idUser: Int, idDoctor: Int){
        ratingViewModel.getRating(idUser, idDoctor).observe(viewLifecycleOwner) {rating ->
            if (rating.rating != 0){
                setStarRating(rating.rating)
            }
        }
    }
    fun setStarRating(star:Int){
        if (star == 1){
            binding.checkboxStar1.isChecked = true
            binding.checkboxStar2.isChecked = false
            binding.checkboxStar3.isChecked = false
            binding.checkboxStar4.isChecked = false
            binding.checkboxStar5.isChecked = false
        } else if (star == 2){
            binding.checkboxStar1.isChecked = true
            binding.checkboxStar2.isChecked = true
            binding.checkboxStar3.isChecked = false
            binding.checkboxStar4.isChecked = false
            binding.checkboxStar5.isChecked = false
        } else if (star == 3){
            binding.checkboxStar1.isChecked = true
            binding.checkboxStar2.isChecked = true
            binding.checkboxStar3.isChecked = true
            binding.checkboxStar4.isChecked = false
            binding.checkboxStar5.isChecked = false
        } else if (star == 4){
            binding.checkboxStar1.isChecked = true
            binding.checkboxStar2.isChecked = true
            binding.checkboxStar3.isChecked = true
            binding.checkboxStar4.isChecked = true
            binding.checkboxStar5.isChecked = false
        } else if (star == 5){
            binding.checkboxStar1.isChecked = true
            binding.checkboxStar2.isChecked = true
            binding.checkboxStar3.isChecked = true
            binding.checkboxStar4.isChecked = true
            binding.checkboxStar5.isChecked = true
        }
    }
    fun getDoctorById(idUser: Int){
        doctorViewModel.getDoctor(idUser).observe(viewLifecycleOwner) { doctorResponse ->
            if (doctorResponse.idDoctor != 0){
                userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
                    user = userResponse
                    isRatingDoctor(user.idUser, doctorResponse.idDoctor)
                }
                doctor = doctorResponse
                if (!doctorResponse.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(doctorResponse.avatar).into(binding.avatarUser)
                binding.txtNameUser.text = doctorResponse.nameUser
                if (doctorResponse.birhtday != null){
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(doctorResponse.birhtday)
                    binding.txtBirthday.text = formattedDate
                }
                binding.txtEmail.text = doctorResponse.email
                binding.linearLayoutRating.visibility = View.VISIBLE
                binding.txtRating.text = doctorResponse.ratingDoctor.toString()
                binding.txtDescription.text = doctorResponse.description.toString()
                binding.linearLayoutRatingDoctor.visibility = View.VISIBLE
                binding.linearLayoutDescription.visibility = View.VISIBLE
                binding.btnMedicineUser.visibility = View.VISIBLE
                getListPostUser(idUser)
                setRatingDoctor()
            }
        }
    }
    fun refreshRatingDoctor(){
        doctorViewModel.getDoctor(doctor.idUser).observe(viewLifecycleOwner) { doctorResponse ->
            if (doctorResponse.idDoctor != 0){
                doctor = doctorResponse
                binding.txtRating.text = doctorResponse.ratingDoctor.toString()
            }
        }
    }
    fun getUser(){
        userViewModel.getUser(auth?.email).observe(viewLifecycleOwner){ userResponse ->
            user = userResponse
            binding.icEditUser.visibility = View.VISIBLE
            if (!user.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(user.avatar).into(binding.avatarUser)
            binding.txtNameUser.text = user.nameUser
            if (userResponse.birhtday != null){
                val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(userResponse.birhtday)
                binding.txtBirthday.text = formattedDate
            }
            binding.txtEmail.text = user.email
            getDoctor()
            getListPostUser(user.idUser)


        }
    }

    fun getDoctor(){
        doctorViewModel.getDoctor(user.idUser).observe(viewLifecycleOwner) { doctor ->
            if (doctor.idDoctor != 0){
                binding.txtRating.text = doctor.ratingDoctor.toString()
                binding.txtDescription.text = doctor.description.toString()
                binding.linearLayoutRating.visibility = View.VISIBLE
                binding.linearLayoutDescription.visibility = View.VISIBLE
                binding.btnMedicineUser.visibility = View.VISIBLE
            }
        }
    }
    fun getListPostUser(idUser: Int) {
        postViewModel.getListPostUser(idUser).observe(viewLifecycleOwner) { listPost ->
            listPost?.let {
                val postUserAdapter = PostUserAdapter(requireContext(), listPost, R.layout.item_post_user)
                val navController = Navigation.findNavController(requireView())
                postUserAdapter.setNavController(navController)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = postUserAdapter
            }
        }
    }
}