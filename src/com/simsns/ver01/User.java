package com.simsns.ver01;

import java.util.Vector;

public class User {
    private String id;
    private String password;

    private String email;

    public User(String id, String password, String email) {
        this.id = id;
        this.password =password;
        this.email = email;
    }




    public String getId() {
        return id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail () {
        return  this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static int findUserIndexById(Vector<User> users, String id) {
        int userIdx = -1;

        for (int i = 0; i < users.size(); i++) {
            if(users.get(i).getId().equals(id)){
                userIdx = i;
                System.out.println(i + " "+users.get(i).getId());
            }
        }

        return userIdx;
    }

    @Override
    public String toString() {
        return this.id;
    }

}
