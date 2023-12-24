package com.example.healthcare2.adapter;

import static androidx.navigation.fragment.FragmentKt.findNavController;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcare2.R;
import com.example.healthcare2.data.model.Comment;
import com.example.healthcare2.databinding.ItemNotificationBinding;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Comment> list;
    private int layout;
    private NavController navController;

    // Constructors và các phương thức khác của Adapter

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public NotificationAdapter(Context context, List<Comment> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = list.get(position);
        holder.bind(comment);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNotificationBinding binding;

        public ViewHolder(@NonNull ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Comment comment){
            if (comment.getUser().getAvatar() != null) Glide.with(context).load(comment.getUser().getAvatar()).into(binding.imgPicture);
            String content = "<b>"+comment.getUser().getNameUser()+ "</b>" + " commented on your post.";
            if (comment.getIdMedicine() != null){
                content = "<b>"+comment.getUser().getNameUser()+ "</b>" + " commented on your medicine";
            }
            binding.txtContent.setText(android.text.Html.fromHtml(content));
            binding.constraintLayoutNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (comment.getIdMedicine() != null){
                        Bundle bundle = new Bundle();
                        bundle.putInt("idMedicine", comment.getIdMedicine());
                        navController.navigate(R.id.action_notificationFragment_to_detailMedicineFragment, bundle);
                    } else if (comment.getIdPost() != null){
                        Bundle bundle = new Bundle();
                        bundle.putInt("idPost", comment.getIdPost());
                        navController.navigate(R.id.action_notificationFragment_to_detailPostFragment, bundle);
                    }
                }
            });
        }
    }
}
