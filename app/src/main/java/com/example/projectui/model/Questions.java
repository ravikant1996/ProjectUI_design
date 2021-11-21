package com.example.projectui.model;

import java.util.ArrayList;

public class Questions {
    private int questionId;
    private int sectionId;
    private String question;
    private ArrayList<Options> arrayList;
    private int answerNr;

    public ArrayList<Options> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Options> arrayList) {
        this.arrayList = arrayList;
    }

    public Questions() {
    }

    public Questions(int questionId, int sectionId, String question, ArrayList<Options> arrayList, int answerNr) {
        this.questionId = questionId;
        this.sectionId = sectionId;
        this.question = question;
        this.arrayList = arrayList;
        this.answerNr = answerNr;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }
}
