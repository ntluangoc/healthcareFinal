package com.example.healthcare2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcare2.R;
import com.example.healthcare2.data.model.Doctor;
import com.example.healthcare2.databinding.ItemDoctorBinding;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Doctor> list;
    private List<Integer> listTotalPost;
    private int layout;
    private NavController navController;

    // Constructors và các phương thức khác của Adapter

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
    public DoctorAdapter(Context context, List<Doctor> list, List<Integer> listTotalPost, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
        this.listTotalPost = listTotalPost;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDoctorBinding binding = ItemDoctorBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = list.get(position);
        int totalPost = listTotalPost.get(position);
        holder.bind(doctor, totalPost);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemDoctorBinding binding;

        public ViewHolder(@NonNull ItemDoctorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Doctor doctor, int totalPost) {
            if (doctor.getAvatar() != null) Glide.with(context).load(doctor.getAvatar()).into(binding.imgAvatar);
            binding.txtNameDoctor.setText(doctor.getNameUser());
            binding.txtTotalPosts.setText(String.valueOf(totalPost));
            binding.txtDoctorDescription.setText(doctor.getDescription());
            setStar(doctor.getRatingDoctor());
            binding.cardDoctor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idUser", doctor.getIdUser());
                    navController.navigate(R.id.action_listDoctorFragment_to_detailUserFragment, bundle);
                }
            });
        }
        public void setStar(float rating) {
            if (rating >= 0f && rating < 0.5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_empty);
                binding.icSao2.setImageResource(R.drawable.ic_star_empty);
                binding.icSao3.setImageResource(R.drawable.ic_star_empty);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 0.5f && rating < 1f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_half);
                binding.icSao2.setImageResource(R.drawable.ic_star_empty);
                binding.icSao3.setImageResource(R.drawable.ic_star_empty);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 1f && rating < 1.5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_empty);
                binding.icSao3.setImageResource(R.drawable.ic_star_empty);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 1.5f && rating < 2f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_half);
                binding.icSao3.setImageResource(R.drawable.ic_star_empty);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 2f && rating < 2.5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_empty);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 2.5f && rating < 3f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_half);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 3f && rating < 3.5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_full);
                binding.icSao4.setImageResource(R.drawable.ic_star_empty);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 3.5f && rating < 4f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_full);
                binding.icSao4.setImageResource(R.drawable.ic_star_half);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 4f && rating < 4.5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_full);
                binding.icSao4.setImageResource(R.drawable.ic_star_full);
                binding.icSao5.setImageResource(R.drawable.ic_star_empty);
            } else if (rating >= 4.5f && rating < 5f) {
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_full);
                binding.icSao4.setImageResource(R.drawable.ic_star_full);
                binding.icSao5.setImageResource(R.drawable.ic_star_half);
            } else if (rating == 5){
                binding.icSao1.setImageResource(R.drawable.ic_star_full);
                binding.icSao2.setImageResource(R.drawable.ic_star_full);
                binding.icSao3.setImageResource(R.drawable.ic_star_full);
                binding.icSao4.setImageResource(R.drawable.ic_star_full);
                binding.icSao5.setImageResource(R.drawable.ic_star_full);
            }
        }

    }
}
