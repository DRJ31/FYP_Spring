package com.suzumiya.model;

import com.suzumiya.model.relationship.PiloCilo;

import java.util.List;

public class Cilo {
    private int id;
    private int syllabus_id;
    private int cilo_id;
    private String content;
    private Tla tla;
    private List<PiloCilo> pilo_cilos;
    private Syllabus syllabus;

    public Cilo() {
    }

    public Syllabus getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(Syllabus syllabus) {
        this.syllabus = syllabus;
    }

    public List<PiloCilo> getPilo_cilos() {
        return pilo_cilos;
    }

    public void setPilo_cilos(List<PiloCilo> pilo_cilos) {
        this.pilo_cilos = pilo_cilos;
    }

    public Tla getTla() {
        return tla;
    }

    public void setTla(Tla tla) {
        this.tla = tla;
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

    public int getCilo_id() {
        return cilo_id;
    }

    public void setCilo_id(int cilo_id) {
        this.cilo_id = cilo_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
