package com.suzumiya.model;

import java.util.List;

public class Pilo {
    private int id;
    private int syllabus_id;
    private int pilo_id;
    private String content;

    public Pilo() {
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
