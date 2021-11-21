package com.example.projectui.model;

import java.util.ArrayList;

public class Sections {
    private int sectionId;
    private String examinationId;
    private String instructions, section_name, section_description;
    private ArrayList<Questions> arrayList;

    public Sections(int sectionId, String examinationId, String instructions, String section_name,
                    String section_description, ArrayList<Questions> arrayList) {
        this.sectionId = sectionId;
        this.examinationId = examinationId;
        this.instructions = instructions;
        this.section_name = section_name;
        this.section_description = section_description;
        this.arrayList = arrayList;
    }

    public Sections() {
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(String examinationId) {
        this.examinationId = examinationId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public String getSection_description() {
        return section_description;
    }

    public void setSection_description(String section_description) {
        this.section_description = section_description;
    }

    public ArrayList<Questions> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Questions> arrayList) {
        this.arrayList = arrayList;
    }
}
