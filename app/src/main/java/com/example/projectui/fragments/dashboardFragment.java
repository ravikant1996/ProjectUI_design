package com.example.projectui.fragments;

import static com.example.projectui.GetStrings.getDay;
import static com.example.projectui.GetStrings.getMonth;
import static com.example.projectui.GetStrings.getYear;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectui.R;
import com.example.projectui.adapters.ExamAdapter;
import com.example.projectui.adapters.SectionAdapter;
import com.example.projectui.databinding.FragmentDashboardBinding;
import com.example.projectui.model.Examination;
import com.example.projectui.model.Options;
import com.example.projectui.model.Questions;
import com.example.projectui.model.Sections;
import com.example.projectui.sharedpreference.ExaminationStorage;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class dashboardFragment extends Fragment {

    FragmentDashboardBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        BottomNavigationView bottomNavigationView = (getActivity()).findViewById(R.id.navigation);
        bottomNavigationView.getMenu().findItem(R.id.dashboard).setChecked(true);


        addExaminationList();

        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, new examFragment()).commit();

            }
        });

        String date = "10/08/23";
        int day = Integer.parseInt(date.substring(0, 2));
        int month = Integer.parseInt(date.substring(3, 5));
        int year = Integer.parseInt(date.substring(6, date.length()));

        Log.e(date, day + " " + month + " " + year);
        Log.e(date, getDay(System.currentTimeMillis())
                + " " + getMonth(System.currentTimeMillis())
                + " " + getYear(System.currentTimeMillis()));


        return binding.getRoot();
    }

    private void addExaminationList() {
        ArrayList<Examination> examinations = new ArrayList<>();
        try {
            ExaminationStorage storage = new ExaminationStorage();
            if (storage != null) {
                examinations.addAll(storage.getFavorites(getActivity()));
                Log.e("Examinations", examinations + "");
            }


        } catch (NullPointerException w) {
//            Log.e("w.getMessage()", w.getMessage());
        }
        if (examinations.size() > 0) {
            binding.none.getRoot().setVisibility(View.GONE);
        } else {
            binding.none.getRoot().setVisibility(View.VISIBLE);
        }
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        ExamAdapter dataAdapter = new ExamAdapter(examinations, getActivity());
        binding.recyclerView.setAdapter(dataAdapter);
        binding.recyclerView.setItemViewCacheSize(50);
    }
}