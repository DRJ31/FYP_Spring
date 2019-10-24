package com.suzumiya.model.user;

public class Avatar {
    private int id;
    private int user_id;
    private String filename;

    public Avatar() {
    }

    public Avatar(int user_id, String filename) {
        this.user_id = user_id;
        this.filename = filename;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
