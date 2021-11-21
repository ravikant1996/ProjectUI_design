package com.example.projectui.adapters;

import static com.example.projectui.GetStrings.showOptionNo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectui.R;
import com.example.projectui.databinding.OptionWithQLayoutBinding;
import com.example.projectui.model.Options;

import java.util.ArrayList;
import java.util.List;

public class OptionWithQAdapter extends RecyclerView.Adapter<OptionWithQAdapter.ViewHolder> {
    Context context;
    public List<Options> arrayList;
    OptionWithQLayoutBinding binding;
    int answerNr = -1;

    public OptionWithQAdapter(ArrayList<Options> list, int answerNr, Context context) {
        this.arrayList = list;
        this.context = context;
        this.answerNr = answerNr;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = OptionWithQLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Options post = arrayList.get(position);
        binding.optionNo.setText(showOptionNo(position));
        binding.option.setText(post.getOption());
        if (position == answerNr) {
            binding.optionNo.setBackgroundResource(R.drawable.boarder_edittext_dark);
            binding.optionNo.setTextColor(ContextCompat.getColor(context, R.color.white));
            binding.constraintOption.setBackground(AppCompatResources.getDrawable(context, R.drawable.boarder_constraint_dark));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OptionWithQLayoutBinding itemsPostsBinding;

        public ViewHolder(@NonNull OptionWithQLayoutBinding postsBinding) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
        }
    }

    public interface AddOption {
        void onCallBack(String option);
    }

}

