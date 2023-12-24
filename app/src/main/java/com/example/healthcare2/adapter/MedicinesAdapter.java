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
import com.example.healthcare2.data.model.Medicine;
import com.example.healthcare2.databinding.ItemMedicinesBinding;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Medicine> list;
    private int layout;

    private NavController navController;

    // Constructors và các phương thức khác của Adapter

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
    public MedicinesAdapter(Context context, List<Medicine> list, int layout) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMedicinesBinding binding = ItemMedicinesBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medicine medicine = list.get(position);
        holder.bind(medicine);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMedicinesBinding binding;

        public ViewHolder(@NonNull ItemMedicinesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Medicine medicine) {
            Glide.with(context)
                    .load(medicine.getImg())
                    .into(binding.imageMedicines);
            binding.txtName.setText(medicine.getNameMedicine());
            binding.txtPrice.setText(String.valueOf(medicine.getPrice()));
            setStar(medicine.getRating());
            binding.constraintLayoutMedicine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idMedicine", medicine.getIdMedicine());
                    navController.navigate(R.id.action_medicinesFragment_to_detailMedicineFragment, bundle);
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
