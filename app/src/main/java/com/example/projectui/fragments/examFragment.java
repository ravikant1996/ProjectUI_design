package com.example.projectui.fragments;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.projectui.GetStrings.checkDate;
import static com.example.projectui.GetStrings.checkTime;
import static com.example.projectui.GetStrings.getIntData;
import static com.example.projectui.GetStrings.getStringData;
import static com.example.projectui.GetStrings.hideKeyboardFrom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectui.R;
import com.example.projectui.adapters.OptionAdapter;
import com.example.projectui.adapters.QuestionAdapter;
import com.example.projectui.adapters.SectionAdapter;
import com.example.projectui.databinding.FragmentExamBinding;
import com.example.projectui.model.Examination;
import com.example.projectui.model.Options;
import com.example.projectui.model.Questions;
import com.example.projectui.model.Sections;
import com.example.projectui.sharedpreference.ExaminationStorage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;


public class examFragment extends Fragment implements OptionAdapter.AddAnswer, QuestionAdapter.Notify {
    FragmentExamBinding binding;
    AppCompatActivity activity;
    int answerNo = -1;
    boolean isUp;
    ArrayList<Examination> examinationArrayList;
    ArrayList<Sections> sectionsArrayList;
    ArrayList<Questions> questionsArrayList;
    ArrayList<Options> optionsArrayList;

    SectionAdapter sectionAdapter;
    QuestionAdapter questionAdapter;
    OptionAdapter optionAdapter;
    Calendar myCalendar;
    String examinationId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentExamBinding.inflate(inflater, container, false);

        activity = (AppCompatActivity) getActivity();

        BottomNavigationView bottomNavigationView = (getActivity()).findViewById(R.id.navigation);
        bottomNavigationView.getMenu().findItem(R.id.exams).setChecked(true);

        if (activity != null) {
            activity.setSupportActionBar(binding.toolbar);
            activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        myCalendar = Calendar.getInstance();
        examinationArrayList = new ArrayList<>();
        sectionsArrayList = new ArrayList<>();
        questionsArrayList = new ArrayList<>();
        optionsArrayList = new ArrayList<>();


        ArrayList<Examination> examinations = new ArrayList<>();
        try {
            ExaminationStorage storage = new ExaminationStorage();
            if (storage != null) {
                examinations.addAll(storage.getFavorites(getActivity()));
                examinationId = "EXAM0" + (examinations.size() + 1);
            }
        } catch (NullPointerException w) {
            examinationId = "EXAM01";
        }

        binding.toolbar.setNavigationIcon(R.drawable.ac_back);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nextBtn.getTag().equals("Next")) {
                    goToDashBoard(getActivity());
                } else {
                    binding.constraintFirst.setVisibility(VISIBLE);
                    binding.nextBtn.setText("Next");
                    binding.nextBtn.setTag("Next");
                    binding.constraintSection.setVisibility(GONE);
                    binding.addAnotherSection.setVisibility(GONE);
                    viewVisibleAnimatorFirst(binding.constraintFirst);
                    viewGoneAnimatorSecond(binding.constraintSection);
                    viewGoneAnimatorSecond(binding.addAnotherSection);

                }
            }
        });


        binding.nextBtn.setTag("Next");
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.nextBtn.getTag().equals("Next")) {
                    if (binding.moduleName.getText().toString().trim().isEmpty()
                            || binding.date.getText().toString().trim().isEmpty()
                            || binding.time.getText().toString().trim().isEmpty()
                            || binding.timeframe.getText().toString().trim().isEmpty()
                            || binding.totalMarks.getText().toString().trim().isEmpty()
                            || binding.category.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
                    } else if (!checkDate(binding.date.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Enter a valid date", Toast.LENGTH_SHORT).show();
                    } else if (!checkTime(binding.date.getText().toString().trim() + " " +
                            binding.time.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "Enter a valid time", Toast.LENGTH_SHORT).show();
                    } else if (binding.duration.getText().toString().trim()
                            .replace(" ", "")
                            .replace("|", "")
                            .length() < 3) {
                        Toast.makeText(getActivity(), "Enter a valid duration period", Toast.LENGTH_SHORT).show();
                    } else if (binding.timeframe.getText().toString().trim()
                            .replace(" ", "")
                            .replace("|", "")
                            .length() < 3) {
                        Toast.makeText(getActivity(), "Enter a valid timeframe period", Toast.LENGTH_SHORT).show();
                    } else {
                        binding.constraintFirst.setVisibility(GONE);
                        binding.nextBtn.setText("Save");
                        binding.nextBtn.setTag("Save");
                        binding.addAnotherSection.setVisibility(VISIBLE);
                        binding.constraintSection.setVisibility(VISIBLE);

                        viewGoneAnimatorFirst(binding.constraintFirst);
                        viewVisibleAnimatorSecond(binding.constraintSection);
                        viewVisibleAnimatorSecond(binding.addAnotherSection);

                    }
                } else {
                    // save
                    if (binding.instruction.getText().toString().trim().isEmpty()
                            || binding.sectionTitle.getText().toString().trim().isEmpty()
                            || binding.sectionDescription.getText().toString().isEmpty()
                            || questionsArrayList.size() == 0) {
                        Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
                    } else {
                        if (questionsArrayList.size() != 0) {
                            sectionsArrayList.add(
                                    new Sections(sectionsArrayList.size() + 1,
                                            examinationId,
                                            binding.instruction.getText().toString().trim(),
                                            binding.sectionTitle.getText().toString().trim(),
                                            binding.sectionDescription.getText().toString().trim(),
                                            questionsArrayList));
                            saveFields();
                        } else {
                            Toast.makeText(getActivity(), "Please add question", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        addSectionList();
        addQuestionList();
        addOptionList();


        binding.addAnotherSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionsArrayList.size() == 0
                        || binding.instruction.getText().toString().trim().isEmpty()
                        || binding.sectionTitle.getText().toString().trim().isEmpty()
                        || binding.sectionDescription.getText().toString().isEmpty()
                        || questionsArrayList.size() == 0) {
                    Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
                } else if (questionsArrayList.size() != 0) {

                    ArrayList<Questions> arrayListTemp = new ArrayList<>();
                    arrayListTemp.addAll(questionsArrayList);
                    sectionsArrayList.add(
                            new Sections(sectionsArrayList.size() + 1,
                                    examinationId,
                                    binding.instruction.getText().toString().trim(),
                                    binding.sectionTitle.getText().toString().trim(),
                                    binding.sectionDescription.getText().toString().trim(),
                                    arrayListTemp));

                    questionsArrayList.clear();
                    binding.instruction.setText("");
                    binding.sectionTitle.setText("");
                    binding.sectionDescription.setText("");
                    if (sectionsArrayList.size() > 0) {
                        binding.recyclerViewSection.setVisibility(VISIBLE);
                    } else {
                        binding.recyclerViewSection.setVisibility(GONE);
                    }
                    sectionAdapter.notifyDataSetChanged();
                    questionAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please add question", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.question.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Enter question", Toast.LENGTH_SHORT).show();
                } else if (optionsArrayList.size() < 4) {
                    Toast.makeText(getActivity(), "Enter options", Toast.LENGTH_SHORT).show();
                } else if (answerNo == -1) {
                    Toast.makeText(getActivity(), "Select answer number", Toast.LENGTH_SHORT).show();
                } else {
                    ArrayList<Options> optionsArrayTemp = new ArrayList<>();
                    optionsArrayTemp.addAll(optionsArrayList);
                    int ans = answerNo;

                    questionsArrayList.add(new Questions(
                            questionsArrayList.size() + 1,
                            sectionsArrayList.size() + 1,
                            binding.question.getText().toString().trim(),
                            optionsArrayTemp,
                            ans)
                    );
                    answerNo = -1;
                    optionsArrayList.clear();
                    binding.question.setText("");
                    binding.constraintOptionField.setVisibility(VISIBLE);
                    questionAdapter.notifyDataSetChanged();
                    optionAdapter.notifyDataSetChanged();

                }
            }
        });


        binding.option.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (!binding.option.getText().toString().trim().isEmpty()) {
                        optionsArrayList.add(new Options(
                                optionsArrayList.size() + 1,
                                questionsArrayList.size() + 1,
                                binding.option.getText().toString().trim()
                        ));
                        optionAdapter.notifyDataSetChanged();
                        binding.option.setText("");
                        if (optionsArrayList.size() < 4) {
                            binding.constraintOptionField.setVisibility(VISIBLE);
                        } else {
                            hideKeyboardFrom(getActivity(), v);
                            binding.constraintOptionField.setVisibility(GONE);
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        return binding.getRoot();
    }

    private void checkFields() {

    }

    private void saveFields() {
        try {
            String classroom = binding.classroomTypeSpinner.getSelectedItem().toString();
            String moduleName = getStringData(binding.moduleName);
            String category = getStringData(binding.category);
            String date = getStringData(binding.date);
            String time = getStringData(binding.time);
            String duration = getStringData(binding.duration);
            String timeframe = getStringData(binding.timeframe);
            int totalMarks = getIntData(binding.totalMarks);

            String instruction = getStringData(binding.instruction);
            String sectionTitle = getStringData(binding.sectionTitle);
            String sectionDescription = getStringData(binding.sectionDescription);
            String question = getStringData(binding.question);
            String option = getStringData(binding.option);


            Examination y = new Examination();
            y.setArrayList(sectionsArrayList);
            y.setCategory(category);
            y.setClassroom(classroom);
            y.setDate(date);
            y.setTime(time);
            y.setDuration(duration);
            y.setExaminationId(examinationId);
            y.setSyllabus(moduleName);
            y.setMarks(totalMarks);
            y.setTime_frame(timeframe);
            examinationArrayList.add(y);

            ExaminationStorage storage = new ExaminationStorage();
            storage.addFavorite(getActivity(), y);
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
            goToDashBoard(getActivity());
        } catch (NullPointerException w) {
            //
        }
    }

    private void goToDashBoard(FragmentActivity activity) {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, new dashboardFragment()).commit();

    }


    private void addSectionList() {
        binding.recyclerViewSection.setHasFixedSize(true);
        binding.recyclerViewSection.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewSection.setItemAnimator(new DefaultItemAnimator());
        sectionAdapter = new SectionAdapter(sectionsArrayList, getActivity());
        binding.recyclerViewSection.setAdapter(sectionAdapter);
        binding.recyclerViewSection.setItemViewCacheSize(50);
    }

    private void addOptionList() {
        binding.recyclerViewOptions.setHasFixedSize(true);
        binding.recyclerViewOptions.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewOptions.setItemAnimator(new DefaultItemAnimator());
        optionAdapter = new OptionAdapter(optionsArrayList, getActivity(), this);
        binding.recyclerViewOptions.setAdapter(optionAdapter);
        binding.recyclerViewOptions.setItemViewCacheSize(50);
    }

    private void addQuestionList() {
        binding.recyclerViewQuestion.setHasFixedSize(true);
        binding.recyclerViewQuestion.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerViewQuestion.setItemAnimator(new DefaultItemAnimator());
        questionAdapter = new QuestionAdapter(questionsArrayList, getActivity(), this);
        binding.recyclerViewQuestion.setAdapter(questionAdapter);
        binding.recyclerViewQuestion.setItemViewCacheSize(50);
    }

    @Override
    public void onCallBack(int ansNo) {
        answerNo = ansNo;
    }

    @Override
    public void onNotify() {
        questionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEditQuestion(int questionId, int sectionId, String question) {
        if (questionsArrayList.size() > 0) {
            for (int i = 0; i < questionsArrayList.size(); i++) {
                int qId = questionsArrayList.get(i).getQuestionId();
                int sId = questionsArrayList.get(i).getSectionId();
                if (qId == questionId && sId == sectionId) {
                    questionsArrayList.get(i).setQuestion(question);
                    questionAdapter.notifyItemChanged(i);
                    questionAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        } else if (sectionsArrayList.size() > 0) {
            for (int y = 0; y < sectionsArrayList.size(); y++) {
                ArrayList<Questions> questionsArrayList = new ArrayList<>();
                questionsArrayList.addAll(sectionsArrayList.get(y).getArrayList());
                for (int i = 0; i < questionsArrayList.size(); i++) {
                    int qId = questionsArrayList.get(i).getQuestionId();
                    int sId = questionsArrayList.get(i).getSectionId();
                    if (qId == questionId && sId == sectionId) {
                        questionsArrayList.get(i).setQuestion(question);
                        questionAdapter.notifyItemChanged(i);
                        questionAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        }else {
            Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewGoneAnimatorFirst(final View view) {

        view.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                        view.setVisibility(GONE);
                        view.setAnimation(slideUp);
                    }
                });

    }

    private void viewVisibleAnimatorFirst(final View view) {

        view.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                        view.setVisibility(VISIBLE);
                        view.setAnimation(slideDown);

                    }
                });
    }

    private void viewGoneAnimatorSecond(final View view) {

        view.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Animation slideUp = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
                        view.setVisibility(GONE);
                        view.setAnimation(slideUp);
                    }
                });

    }

    private void viewVisibleAnimatorSecond(final View view) {

        view.animate()
                .alpha(1f)
                .setDuration(500)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Animation slideDown = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
                        view.setVisibility(VISIBLE);
                        view.setAnimation(slideDown);

                    }
                });
    }
}