package com.example.projectui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetStrings {

    private static final String FULL_TIME_FORMAT = "dd/MM/yy HH:mm:ss";

    public static String getStringData(EditText text) {
        return text.getText().toString().trim();
    }

    public static int getIntData(EditText text) {
        return Integer.parseInt(text.getText().toString().trim());
    }

    public static long getLongData(EditText text) {
        return Long.parseLong(text.getText().toString().trim());
    }

    public static String showOptionNo(int pos) {
        String optionNo = "";
        if (pos == 0) {
            optionNo = "A";
        } else if (pos == 1) {
            optionNo = "B";
        } else if (pos == 2) {
            optionNo = "C";
        } else if (pos == 3) {
            optionNo = "D";
        }
        return optionNo;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String getTimeDate(long timestamp) {
        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
            Date netDate = (new Date(timestamp));
            return datetimeFormatter1.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static String getTimeDateFull(long timestamp) {
        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat(FULL_TIME_FORMAT, Locale.getDefault());
            Date netDate = (new Date(timestamp));
            return datetimeFormatter1.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static String getDay(long timestamp) {
        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("dd", Locale.getDefault());
            Date netDate = (new Date(timestamp));
            return datetimeFormatter1.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static String getMonth(long timestamp) {
        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("M", Locale.getDefault());
            Date netDate = (new Date(timestamp));
            return datetimeFormatter1.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static String getYear(long timestamp) {
        try {
            SimpleDateFormat datetimeFormatter1 = new SimpleDateFormat("yyyy", Locale.getDefault());
            Date netDate = (new Date(timestamp));
            return datetimeFormatter1.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }

    public static boolean checkDate(String date) {
        boolean flag = true;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        formatter.setLenient(false);
        Date sDate;
        Date cDate;

        try {
            sDate = (Date) formatter.parse(date);
            cDate = (Date) formatter.parse(getTimeDate(System.currentTimeMillis()));
            if (cDate != null) {
                if (sDate != null) {
                    if (cDate.getTime() > sDate.getTime()) {
                        //Entered date is backdated from current date
                        flag = false;
                    } else {
                        //Entered date is updated from current date
                        flag = true;
                    }
                }
            }
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkTime(String datetime) {
        boolean flag = true;
//        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatter = new SimpleDateFormat(FULL_TIME_FORMAT, Locale.getDefault());
        formatter.setLenient(false);
        Date sDate;
        Date cDate;
        try {
            sDate = (Date) formatter.parse(datetime);
            cDate = (Date) formatter.parse(getTimeDateFull(System.currentTimeMillis()));
            if (cDate != null) {
                if (sDate != null) {
                    if (cDate.getTime() > sDate.getTime()) {
                        //Entered date is backdated from current date
                        flag = false;
                    } else {
                        //Entered date is updated from current date
                        flag = true;
                    }
                }
            }
        } catch (ParseException e) {
            flag = false;
        }
        return flag;
    }
}
