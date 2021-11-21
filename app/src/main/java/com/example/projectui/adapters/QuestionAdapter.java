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

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {
    Context context;
    public List<Questions> arrayList;
    QuestionLayoutBinding binding;
    QuestionAdapter adapter;
    Notify notify;

    public QuestionAdapter(ArrayList<Questions> list, Context context, Notify notify1) {
        this.arrayList = list;
        this.context = context;
        this.adapter = this;
        this.notify = notify1;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = QuestionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, notify);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Questions post = arrayList.get(position);

        ArrayList<Options> optionsArrayList = new ArrayList<>();
        optionsArrayList.addAll(post.getArrayList());

        Log.e("Option List", post.getArrayList().toString());

        binding.question.setText("Q" + (position + 1) + ": " + post.getQuestion());

        binding.save.setTag("edit");
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.save.getTag().equals("edit")) {
                    Toast.makeText(context, "clicked edited", Toast.LENGTH_SHORT).show();
                    binding.question.setEnabled(true);
                    binding.question.setFocusable(true);
                    binding.question.setCursorVisible(true);
                    binding.save.setTag("save");
                    binding.save.setText("Save");
                } else {
                    if (binding.question.getText().toString().trim().isEmpty()){
                        Toast.makeText(context, "Enter question", Toast.LENGTH_SHORT).show();
                    }else {
                        binding.save.setTag("edit");
                        binding.save.setText("Edit");
                        binding.question.setEnabled(false);
                        notify.onEditQuestion(post.getQuestionId(), post.getSectionId(), binding.question.getText().toString().trim());
                    }
                }
            }
        });

        binding.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Do you want to delete this question?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                arrayList.remove(holder.getAdapterPosition());
                                adapter.notifyItemRemoved(holder.getAdapterPosition());
//                                adapter.notifyItemChanged(holder.getAdapterPosition());
                                notifyItemRangeChanged(holder.getAdapterPosition(), arrayList.size());
                                notify.onNotify();
                            }
                        });
                alertDialog.show();
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
        Notify notify;

        public ViewHolder(@NonNull QuestionLayoutBinding postsBinding, Notify notify1) {
            super(binding.getRoot());
            itemsPostsBinding = postsBinding;
            this.notify = notify1;
        }
    }

    public interface Notify {
        void onNotify();
        void onEditQuestion(int questionId, int sectionId, String question);
    }
}

