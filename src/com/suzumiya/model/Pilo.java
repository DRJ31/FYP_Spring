package com.suzumiya.model;

public class Pilo {
    private int id;
    private int syllabus_id;
    private int pilo_id;
    private String content;
    private Pilo_Cilo pilo_cilo;

    public Pilo() {
    }

    public Pilo_Cilo getPilo_cilo() {
        return pilo_cilo;
    }

    public void setPilo_cilo(Pilo_Cilo pilo_cilo) {
        this.pilo_cilo = pilo_cilo;
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
