package com.suzumiya.model;

import com.suzumiya.model.relationship.PiloCilo;

import java.util.List;

public class Pilo {
    private int id;
    private int syllabus_id;
    private int pilo_id;
    private String content;
    private List<PiloCilo> pilo_cilos;

    public Pilo() {
    }

    public List<PiloCilo> getPilo_cilos() {
        return pilo_cilos;
    }

    public void setPilo_cilos(List<PiloCilo> pilo_cilos) {
        this.pilo_cilos = pilo_cilos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSyllabus_id() {
        return syllabus_id;
    }

    public void setSyllabus_id(int syllabus_id) {
        this.syllabus_id = syllabus_id;
    }

    public int getPilo_id() {
        return pilo_id;
    }

    public void setPilo_id(int pilo_id) {
        this.pilo_id = pilo_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
