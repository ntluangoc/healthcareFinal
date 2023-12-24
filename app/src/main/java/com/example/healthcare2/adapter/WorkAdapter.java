package com.example.healthcare2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthcare2.data.model.Work;
import com.example.healthcare2.databinding.ItemWorkBinding;
import com.example.healthcare2.viewmodel.WorkViewModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<Work> list;
    private int layout;
    private WorkViewModel workViewModel;

    public WorkAdapter(Context context, List<Work> list, int layout, WorkViewModel workViewModel) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
        this.layout = layout;
        this.workViewModel = workViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWorkBinding binding = ItemWorkBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Work work = list.get(position);
        holder.bind(work);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWorkBinding binding;

        public ViewHolder(@NonNull ItemWorkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Work work) {
            Timestamp timestamp = work.getTime(); // Đối tượng Timestamp từ work

// Convert Timestamp sang Date
            Date date = new Date(timestamp.getTime());

            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            outputFormat.setTimeZone(TimeZone.getDefault()); // Đặt múi giờ theo múi giờ của thiết bị
            String formattedTime = outputFormat.format(date);

//            System.out.println(formattedTime);
            binding.txtTimeNextWork.setText(formattedTime);
            binding.txtTitleNextWork.setText(work.getTitle());
            binding.txtNoteNextWork.setText(work.getNote());
            binding.checkboxNextWork.setChecked(work.isCheck());
            //
            binding.checkboxNextWork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    work.setCheck(binding.checkboxNextWork.isChecked());
                    workViewModel.updateIsCheckWork(work);
                }
            });
        }
    }
}
