package com.simsns.ver02_2.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;

    private String email;

    private String password;

    private boolean isAdmin;

    public User(String id,String email, String password){
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    } // userDao.checkUserPassword

    public boolean getIsAdmin () {
        return this.isAdmin;
    }

    public void setIsAdmin () {
        this.isAdmin = !this.isAdmin;
    }
    @Override
    public String toString() {
        return this.id;
    }
}
