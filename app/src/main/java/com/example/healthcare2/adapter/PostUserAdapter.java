package com.example.healthcare2.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.healthcare2.R;
import com.example.healthcare2.data.model.Post;
import com.example.healthcare2.databinding.ItemPostUserBinding;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PostUserAdapter extends RecyclerView.Adapter<PostUserAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Post> list;
    private int layout;
    private NavController navController;

    // Constructors và các phương thức khác của Adapter

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
    public PostUserAdapter(Context context, List<Post> list, int layout) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPostUserBinding binding = ItemPostUserBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = list.get(position);
        holder.bind(post);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemPostUserBinding binding;

        public ViewHolder(@NonNull ItemPostUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Post post) {
            binding.txtTitle.setText(post.getTitle());
            if (!post.getImg().isEmpty()) Glide.with(context).load(post.getImg()).into(binding.imgPost);
            binding.txtTitle.setText(post.getTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(new Date(post.getCreated_at().getTime()));
            binding.txtTime.setText(formattedDate);
            binding.txtContent.setText(Html.fromHtml(post.getContent()));
            // Set other data to binding views
            binding.cardPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("idPost", post.getIdPost());
                    navController.navigate(R.id.action_detailUserFragment_to_detailPostFragment, bundle);
                }
            });
        }
    }
}
