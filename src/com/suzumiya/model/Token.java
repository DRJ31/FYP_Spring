package com.suzumiya.model;

import com.suzumiya.controller.EncryptController;

public class Token {
    private String token;
    private String old_pass;
    private String new_pass;

    public Token() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOld_pass() {
        return old_pass;
    }

    public void setOld_pass(String old_pass) {
        this.old_pass = old_pass;
    }

    public String getNew_pass() {
        return new_pass;
    }

    public void setNew_pass(String new_pass) {
        this.new_pass = new_pass;
    }

    public void encrypt(){
        EncryptController e = new EncryptController();
        this.old_pass = e.encrypt(old_pass);
        this.new_pass = e.encrypt(new_pass);
    }
}
