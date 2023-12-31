package com.simsns.ver02_1.controller;

import com.simsns.ver02_1.model.Post;
import com.simsns.ver02_1.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class DataUtil {
    private static final String userData = "data/users.txt";
    private static final String postData = "data/posts.txt";
    private static final File postFile = new File(postData);
    private static final File userFile = new File(userData);

    public static TreeMap<Integer, Post> getPosts (){
        TreeMap<Integer, Post> posts = null;

        if (postFile.exists()) {
            try (
                    FileInputStream fin = new FileInputStream(postData);
                    BufferedInputStream bin = new BufferedInputStream(fin);
                    ObjectInputStream oin =new ObjectInputStream(bin);
                    ) {

                posts = (TreeMap<Integer, Post>) oin.readObject();

            } catch ( ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
        return posts == null ? new TreeMap<>() : posts;
    }

    public static void reservePosts (TreeMap<Integer, Post> posts)  {

        try (
                FileOutputStream fout = new FileOutputStream(postData);
                BufferedOutputStream bout = new BufferedOutputStream(fout);
                ObjectOutputStream oout = new ObjectOutputStream(bout);
                ) {

            oout.writeObject(posts);

        } catch (IOException e ) {
            e.getStackTrace();
        }

    }

    public static HashMap<UUID, User> getUsers () {
        HashMap<UUID, User> users = null;

        if (userFile.exists()) {
            try (
                    FileInputStream fin = new FileInputStream(userData);
                    BufferedInputStream bin = new BufferedInputStream(fin);
                    ObjectInputStream oin =new ObjectInputStream(bin);
            ) {

                users = (HashMap<UUID, User>)oin.readObject();

            } catch ( ClassNotFoundException | IOException e) {
                e.getStackTrace();
            }
        }
        return users == null ? new HashMap<>() : users;
    }

    public static void reserveUsers (HashMap<UUID, User> users) {
        try (
                FileOutputStream fout = new FileOutputStream(userData);
                BufferedOutputStream bout = new BufferedOutputStream(fout);
                ObjectOutputStream oout = new ObjectOutputStream(bout);
        ) {

            oout.writeObject(users);

        }catch (IOException e ) {
            e.getStackTrace();
        }
    }
}
