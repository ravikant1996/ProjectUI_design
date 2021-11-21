package com.example.projectui.model;

public class Options {
    int optionId;
    int questionId;
    String option;


    public Options(int optionId, int questionId, String option) {
        this.optionId = optionId;
        this.questionId = questionId;
        this.option = option;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return "Options{" +
                "optionId=" + optionId +
                ", questionId=" + questionId +
                ", option='" + option + '\'' +
                '}';
    }
}
