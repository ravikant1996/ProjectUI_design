package com.example.projectui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.projectui.databinding.SectionLayoutBinding;
import com.example.projectui.model.Options;
import com.example.projectui.model.Questions;
import com.example.projectui.model.Sections;

import java.util.ArrayList;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {
    Context context;
    public List<Sections> arrayList;
    SectionLayoutBinding binding;

    public SectionAdapter(ArrayList<Sections> list, Context context) {
        this.arrayList = list;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = SectionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sections post = arrayList.get(position);

        binding.instruction.setText(post.getInstructions());
        binding.sectionTitle.setText(post.getSection_name());
        binding.sectionDescription.setText(post.getSection_description());

        ArrayList<Questions> questionsArrayList = new ArrayList<>();
        questionsArrayList.addAll(post.getArrayList());

        binding.recyclerViewQuestion.setHasFixedSize(true);
        binding.recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewQuestion.setItemAnimator(new DefaultItemAnimator());
        QuestionInSAdapter dataAdapter = new QuestionInSAdapter(questionsArrayList, context);
        binding.recyclerViewQuestion.setAdapter(dataAdapter);
        binding.recyclerViewQuestion.setItemViewCacheSize(50);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SectionLayoutBinding itemsPostsBinding;

        public ViewHolder(@NonNull SectionLayoutBinding postsBinding) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
        }
    }

}

