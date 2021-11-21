package com.example.projectui.model;

import java.util.ArrayList;

public class Examination {
    String examinationId;
    String classroom, syllabus;
    String date, time;
    String duration, time_frame;
    int marks;
    String category;
    ArrayList<Sections> arrayList;

    public Examination(String examinationId, String classroom, String syllabus, String date, String time, String duration,
                       String time_frame, int marks, String category, ArrayList<Sections> arrayList) {
        this.examinationId = examinationId;
        this.classroom = classroom;
        this.syllabus = syllabus;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.time_frame = time_frame;
        this.marks = marks;
        this.category = category;
        this.arrayList = arrayList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setTime_frame(String time_frame) {
        this.time_frame = time_frame;
    }

    public Examination() {
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDuration() {
        return duration;
    }

    public String getTime_frame() {
        return time_frame;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Sections> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Sections> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        return "Examination{" +
                "examinationId='" + examinationId + '\'' +
                ", classroom='" + classroom + '\'' +
                ", syllabus='" + syllabus + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", duration=" + duration +
                ", time_frame=" + time_frame +
                ", marks=" + marks +
                ", category='" + category + '\'' +
                ", arrayList=" + arrayList +
                '}';
    }
}
