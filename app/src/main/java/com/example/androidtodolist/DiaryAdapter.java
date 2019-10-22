package com.example.androidtodolist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ToDoViewHolder> {

    public List<Diary> Tasks;
    private OnItemClicked onClick;

    public interface OnItemClicked {
        void onClickItemDelete(int position);
        void onClickItemUpdate(int position);
    }

    public void setOnClick(OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public DiaryAdapter(Runnable mainActivity, List<Diary> tasks) {
        Tasks = tasks;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, final int position) {
        holder.tvTitle.setText(Tasks.get(position).getTitle());
        holder.tvContent.setText(Tasks.get(position).getContent());
        holder.tvDate.setText(Tasks.get(position).getDatetime());


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemDelete(position);
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClick.onClickItemUpdate(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (Tasks == null) {
            return 0;
        }
        return Tasks.size();
    }

    class ToDoViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvContent;
        TextView tvDate;

        Button btnUpdate, btnDelete;

        public ToDoViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate = itemView.findViewById(R.id.tvDate);

            btnUpdate = itemView.findViewById(R.id.updateTask);
            btnDelete = itemView.findViewById(R.id.deleteTask);
        }
    }
}

















