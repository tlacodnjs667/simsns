package com.simsns.ver02.model;

import java.util.UUID;

public class User {
    private String id;

    private String email;

    private String password;

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

    @Override
    public String toString() {
        return this.id;
    }
}
