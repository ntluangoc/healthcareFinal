package com.example.healthcare2.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.healthcare2.R
import com.example.healthcare2.adapter.CommentAdapter
import com.example.healthcare2.data.model.Comment
import com.example.healthcare2.data.model.Rating
import com.example.healthcare2.data.model.User
import com.example.healthcare2.databinding.FragmentCommentBinding
import com.example.healthcare2.utils.SharedViewModel
import com.example.healthcare2.viewmodel.CommentViewModel
import com.example.healthcare2.viewmodel.MedicineViewModel
import com.example.healthcare2.viewmodel.PostViewModel
import com.example.healthcare2.viewmodel.RatingViewModel
import com.example.healthcare2.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CommentFragment() : Fragment() {
    private lateinit var binding: FragmentCommentBinding
    private lateinit var medicineViewModel: MedicineViewModel
    private lateinit var postViewModel: PostViewModel
    private lateinit var commentViewModel: CommentViewModel
    private lateinit var ratingViewModel: RatingViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var recyclerView: RecyclerView
    private var listComment: List<Comment> = ArrayList<Comment>()
    private var listRating: List<Rating> = ArrayList<Rating>()
    private var idMedicine: Int? = null
    private var idPost: Int? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var user: User
    val sharedViewModel : SharedViewModel by activityViewModels()

    constructor(idMedicine: Int?, idPost: Int?) : this() {
        this.idMedicine = idMedicine
        this.idPost = idPost
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater)
        commentViewModel = ViewModelProvider(this).get(CommentViewModel::class.java)
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        ratingViewModel = ViewModelProvider(this).get(RatingViewModel::class.java)
        medicineViewModel = ViewModelProvider(this).get(MedicineViewModel::class.java)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        recyclerView = binding.recyclerviewComment
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        // Initialize Firebase Auth
        auth = Firebase.auth

        if (idMedicine != null){
            getRatingCommentMedicine(idMedicine!!)
        } else if (idPost != null){
            getRatingCommentPost(idPost!!)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRate()
        if (idMedicine != null){
            addCommentMedicine()
            binding.txtSeeAlls.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("idMedicine", idMedicine!!)
                findNavController().navigate(
                    R.id.action_detailMedicineFragment_to_listCommentFrament,
                    bundle
                )
            }
        } else if (idPost != null){
            addCommentPost()
            binding.txtSeeAlls.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("idPost", idPost!!)
                findNavController().navigate(
                    R.id.action_detailPostFragment_to_listCommentFrament,
                    bundle
                )
            }
        }
    }
    fun addCommentMedicine(){
        binding.icSendComment.setOnClickListener {
            var ratingComment: Int = 0
            if (binding.checkboxStar5.isChecked) ratingComment = 5
            else if (binding.checkboxStar4.isChecked) ratingComment = 4
            else if (binding.checkboxStar3.isChecked) ratingComment = 3
            else if (binding.checkboxStar2.isChecked) ratingComment = 2
            else if (binding.checkboxStar1.isChecked) ratingComment = 1
            if (ratingComment == 0){
                Log.d("ERROR", "Lỗi: rating hiện tại $ratingComment")
                Toast.makeText(requireContext(), "You must rate!", Toast.LENGTH_SHORT).show()
            } else if (binding.edtComment.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "You must comment!", Toast.LENGTH_SHORT).show()
            } else{
                var comment: Comment = Comment(user.idUser,0, idMedicine!!, binding.edtComment.text.toString())
                var rating: Rating = Rating(user.idUser, 0,0, idMedicine!!, ratingComment)
                commentViewModel.addCommentMedicine(comment)
                ratingViewModel.addRatingMedicine(rating)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    getRatingCommentMedicine(idMedicine!!)
                }, 500)
            }
        }
    }
    fun addCommentPost(){
        binding.icSendComment.setOnClickListener {
            var ratingComment: Int = 0
            if (binding.checkboxStar5.isChecked) ratingComment = 5
            else if (binding.checkboxStar4.isChecked) ratingComment = 4
            else if (binding.checkboxStar3.isChecked) ratingComment = 3
            else if (binding.checkboxStar2.isChecked) ratingComment = 2
            else if (binding.checkboxStar1.isChecked) ratingComment = 1
            if (ratingComment == 0){
                Log.d("ERROR", "Lỗi: rating hiện tại $ratingComment")
                Toast.makeText(requireContext(), "You must rate!", Toast.LENGTH_SHORT).show()
            } else if (binding.edtComment.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "You must comment!", Toast.LENGTH_SHORT).show()
            } else{
                var comment: Comment = Comment(user.idUser, idPost!!,0, binding.edtComment.text.toString())
                var rating: Rating = Rating(user.idUser, 0, idPost!!, 0, ratingComment)
                commentViewModel.addCommentPost(comment)
                ratingViewModel.addRatingPost(rating)
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    // Gọi hàm refreshRatingDoctor() ở đây
                    getRatingCommentPost(idPost!!)
                }, 500)
            }
        }
    }

    fun getCommentMedicine(idMedicine: Int){
        medicineViewModel.getMedicine(idMedicine).observe(viewLifecycleOwner){ medicine ->
            binding.txtRating.text = medicine.rating.toString()
            setStarRatingTotal(medicine.rating)
            //Kiểm tra user hiện tại đã comment chưa
            Log.d("EMAIL", "User email: " + auth.currentUser?.email)
            userViewModel.getUser(auth.currentUser?.email).observe(viewLifecycleOwner){ userResponse ->

                user = userResponse
                Log.d("USER", "User comment: ${user.toString()}")
                commentViewModel.getIsCommentMedicine(idMedicine!!, user.idUser).observe(viewLifecycleOwner){isComment ->
                    if (isComment == 0) binding.constraintLayoutComment.visibility = View.VISIBLE
                    else binding.constraintLayoutComment.visibility = View.GONE
                }
                if (!user.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(user.avatar).into(binding.imgAvatar)
            }

        }
        commentViewModel.getNumAllCommentMedicine(idMedicine).observe(viewLifecycleOwner){ numComment ->
            binding.txtNumComment.text = numComment.toString()
        }
        commentViewModel.getCommentMedicine(idMedicine).observe(viewLifecycleOwner){ comments ->
            listComment = comments
            commentAdapter = CommentAdapter(requireContext(), listComment, listRating, R.layout.item_comment)
            recyclerView.adapter = commentAdapter
            binding.lottiteComment.visibility = View.GONE
            binding.recyclerviewComment.visibility = View.VISIBLE
        }
    }
    fun getCommentPost(idPost: Int){
        postViewModel.getPost(idPost).observe(viewLifecycleOwner){ post ->
            binding.txtRating.text = post.rating.toString()
            setStarRatingTotal(post.rating)
            //Kiểm tra user hiện tại đã comment chưa
            Log.d("EMAIL", "User email: " + auth.currentUser?.email)
            userViewModel.getUser(auth.currentUser?.email).observe(viewLifecycleOwner){ userResponse ->

                user = userResponse
                Log.d("USER", "User comment: ${user.toString()}")
                commentViewModel.getIsCommentPost(idPost!!, user.idUser).observe(viewLifecycleOwner){ isComment ->
                    if (isComment == 0) binding.constraintLayoutComment.visibility = View.VISIBLE
                    else binding.constraintLayoutComment.visibility = View.GONE

                }
                if (!user.avatar.isNullOrEmpty()) Glide.with(requireContext()).load(user.avatar).into(binding.imgAvatar)
            }

        }
        commentViewModel.getNumAllCommentPost(idPost).observe(viewLifecycleOwner){ numPost ->
            binding.txtNumComment.text = numPost.toString()
        }
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
            Log.d("RATING", "list rating comment post: ${listRating.toString()}")
            getCommentPost(idPost)
        }
    }
    fun setRate(){
        binding.checkboxStar1.setOnClickListener {
            binding.txtQuality.text = "Terrible"
            setStarRatingComment(1)
        }
        binding.checkboxStar2.setOnClickListener {
            binding.txtQuality.text = "Poor"
            setStarRatingComment(2)
        }
        binding.checkboxStar3.setOnClickListener {
            binding.txtQuality.text = "Fair"
            setStarRatingComment(3)
        }
        binding.checkboxStar4.setOnClickListener {
            binding.txtQuality.text = "Good"
            setStarRatingComment(4)
        }
        binding.checkboxStar5.setOnClickListener {
            binding.txtQuality.text = "Amazing"
            setStarRatingComment(5)
        }
    }
    fun setStarRatingComment(star: Int){
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
    fun setStarRatingTotal(rating: Float){
        sharedViewModel.rating.value = rating
        if (0 <= rating && rating < 0.5){

        } else if (0.5 <= rating && rating < 1){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_half)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)

        } else if (1 <= rating && rating < 1.5){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (1.5 <= rating && rating < 2){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_half)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (2 <= rating && rating < 2.5){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (2.5 <= rating && rating < 3){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_half)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (3 <= rating && rating < 3.5){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_empty)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (3.5 <= rating && rating < 4){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_half)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (4 <= rating && rating < 4.5){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_empty)
        } else if (4.5 <= rating && rating < 5){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_half)
        } else if (rating == 5f){
            binding.icStarRating1.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating2.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating3.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating4.setImageResource(R.drawable.ic_star_full)
            binding.icStarRating5.setImageResource(R.drawable.ic_star_full)
        }
    }
}