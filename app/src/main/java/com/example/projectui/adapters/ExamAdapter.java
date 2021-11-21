package com.example.projectui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectui.databinding.LayoutExaminationBinding;
import com.example.projectui.databinding.LayoutNoExaminationBinding;
import com.example.projectui.model.Examination;
import com.example.projectui.model.Options;
import com.example.projectui.model.Questions;
import com.example.projectui.model.Sections;

import java.util.ArrayList;
import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ViewHolder> {
    Context context;
    public List<Examination> arrayList;
    LayoutExaminationBinding binding;

    public ExamAdapter(ArrayList<Examination> list, Context context) {
        this.arrayList = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = LayoutExaminationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Examination post = arrayList.get(position);
        binding.examName.setText(post.getClassroom());
        binding.marks.setText(post.getMarks() + " marks");
        binding.timing.setText(post.getTime() + "");
        binding.marksType.setText(post.getCategory() + "");

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LayoutExaminationBinding itemsPostsBinding;

        public ViewHolder(@NonNull LayoutExaminationBinding postsBinding) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
        }
    }

}

