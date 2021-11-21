package com.example.projectui.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectui.databinding.QuestionLayoutBinding;
import com.example.projectui.model.Options;
import com.example.projectui.model.Questions;

import java.util.ArrayList;
import java.util.List;

public class QuestionInSAdapter extends RecyclerView.Adapter<QuestionInSAdapter.ViewHolder> {
    Context context;
    public List<Questions> arrayList;
    QuestionLayoutBinding binding;
    QuestionInSAdapter adapter;

    public QuestionInSAdapter(ArrayList<Questions> list, Context context) {
        this.arrayList = list;
        this.context = context;
        this.adapter = this;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = QuestionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions post = arrayList.get(position);

        ArrayList<Options> optionsArrayList = new ArrayList<>();
        optionsArrayList.addAll(post.getArrayList());

        binding.question.setText("Q" + (position + 1) + ": " + post.getQuestion());

        binding.save.setTag("save");
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.save.getTag().equals("edit")) {
                    Toast.makeText(context, "edited", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.remove.setTag("remove");
        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.save.getTag().equals("edit")) {
                    Toast.makeText(context, "edited", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.recyclerViewOptions.setHasFixedSize(true);
        binding.recyclerViewOptions.setLayoutManager(new LinearLayoutManager(context));
        binding.recyclerViewOptions.setItemAnimator(new DefaultItemAnimator());
        OptionWithQAdapter dataAdapter = new OptionWithQAdapter(optionsArrayList, post.getAnswerNr(), context);
        binding.recyclerViewOptions.setAdapter(dataAdapter);
        binding.recyclerViewOptions.setItemViewCacheSize(50);


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        QuestionLayoutBinding itemsPostsBinding;

        public ViewHolder(@NonNull QuestionLayoutBinding postsBinding) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
        }
    }

}

