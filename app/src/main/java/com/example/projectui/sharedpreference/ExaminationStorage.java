package com.example.projectui.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectui.model.Examination;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ExaminationStorage {
    public static final String PREFS_NAME = "examination_data";
    public static final String EXAM_LIST = "exam_list";
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    public ExaminationStorage() {
        super();
    }

    // This four methods are used for maintaining notificationData.
    public void saveFavorites(Context context, List<Examination> notificationData) {

        try {
            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);
            editor = settings.edit();

            Gson gson = new Gson();
            String jsonFavorites = gson.toJson(notificationData);

            editor.clear();
            editor.putString(EXAM_LIST, jsonFavorites);

            editor.apply();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void clearStorage(Context context) {
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    public void addFavorite(Context context, Examination product) {
        List<Examination> notificationData = getFavorites(context);
        if (notificationData == null)
            notificationData = new ArrayList<Examination>();
        notificationData.add(product);
        saveFavorites(context, notificationData);
    }

    public void removeFavorite(Context context, String id) {
        try {
            ArrayList<Examination> notificationData = getFavorites(context);
            if (notificationData != null) {
                Iterator<Examination> iterator = notificationData.iterator();
                while (iterator.hasNext()) {
                    Examination next = iterator.next();
                    if (next.getExaminationId().equals(id)) {
                        iterator.remove();
                        saveFavorites(context, notificationData);
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Examination> getFavorites(Context context) {
        SharedPreferences settings;
        List<Examination> notificationData = null;

        try {
            settings = context.getSharedPreferences(PREFS_NAME,
                    Context.MODE_PRIVATE);


            if (settings.contains(EXAM_LIST)) {
                String jsonFavorites = settings.getString(EXAM_LIST, null);
                Gson gson = new Gson();
                Examination[] notificationItem = gson.fromJson(jsonFavorites,
                        Examination[].class);

                notificationData = Arrays.asList(notificationItem);
                notificationData = new ArrayList<Examination>(notificationData);
            } else
                return null;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return (ArrayList<Examination>) notificationData;
    }
}
