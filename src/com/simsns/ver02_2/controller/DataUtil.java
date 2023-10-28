package com.simsns.ver02_2.controller;

import com.simsns.ver02_2.model.Post;
import com.simsns.ver02_2.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public class DataUtil {
    private static final String userData = "data/users.txt";
    private static final String postData = "data/posts.txt";
    private static File postFile =new File(postData);

    public static TreeMap<Integer, Post> getPosts (){
        TreeMap<Integer, Post>posts = new TreeMap<>();

        if (!postFile.exists()) {
            return posts;
        }

        try (
                FileInputStream fin = new FileInputStream(postData);
                BufferedInputStream bin = new BufferedInputStream(fin);
                ObjectInputStream oin =new ObjectInputStream(bin);
                ) {

            Object dataFromFile = oin.readObject();
            posts = dataFromFile == null ? posts : (TreeMap<Integer, Post>) dataFromFile;

        } catch ( ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return posts;
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
        try (
                FileInputStream fin = new FileInputStream(userData);
                BufferedInputStream bin = new BufferedInputStream(fin);
                ObjectInputStream oin =new ObjectInputStream(bin);
        ) {

            users = (HashMap<UUID, User>)oin.readObject();

        } catch ( ClassNotFoundException | IOException e) {
            e.getStackTrace();
        }
        return users;
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
