package com.example.projectui.adapters;

import static com.example.projectui.GetStrings.showOptionNo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectui.databinding.OptionLayoutBinding;
import com.example.projectui.model.Options;

import java.util.ArrayList;
import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    Context context;
    public List<Options> arrayList;
    OptionLayoutBinding binding;
    AddAnswer answer;
    private int lastSelectedPosition = -1;
    private RadioButton lastCheckedRB = null;

    public OptionAdapter(ArrayList<Options> list, Context context, AddAnswer answerNo) {
        this.arrayList = list;
        this.context = context;
        this.answer = answerNo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = OptionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, answer);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Options post = arrayList.get(position);
        binding.optionNo.setText(showOptionNo(position));
        binding.option.setText(post.getOption());

        binding.radio.setChecked(lastSelectedPosition == position);

        View.OnClickListener rbClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton checked_rb = (RadioButton) v;
                if(lastCheckedRB != null){
                    lastCheckedRB.setChecked(false);
                }
                lastCheckedRB = checked_rb;
                answer.onCallBack(holder.getAdapterPosition());
            }
        };
        binding.radio.setOnClickListener(rbClick);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        OptionLayoutBinding itemsPostsBinding;
        AddAnswer addAnswer;

        public ViewHolder(@NonNull OptionLayoutBinding postsBinding, AddAnswer answer1) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
            this.addAnswer = answer1;

        }
    }

    public interface AddAnswer {
        void onCallBack(int answerNo);
    }

}

