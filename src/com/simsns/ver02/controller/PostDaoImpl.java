package com.simsns.ver02.controller;

import com.simsns.ver02.model.Post;

import java.util.TreeMap;
import java.util.UUID;
import java.util.Set;

public class PostDaoImpl implements PostDao {

    private static PostDaoImpl instance = new PostDaoImpl();
    private final TreeMap<Integer, Post> posts = new TreeMap<>();
    private static int count = 0;

    private PostDaoImpl () {}

    public static PostDaoImpl getInstance () {
        if (instance == null)
        {
            instance = new PostDaoImpl();
        }
        return  instance;
    }

    @Override
    public TreeMap<Integer, Post> get () {
        return posts;
    }

    @Override
    public Post get (int index) {
        if (posts.get(index) == null) {
            return null;
        }
        return posts.get(index);
    }

    @Override
    public boolean checkOwner (UUID userIndex, int postIndex) {
        return posts.get(postIndex).getOwner().equals(userIndex);
    }

    @Override
    public void addLike (int index) {
        posts.get(index).setLikes(posts.get(index).getLikes() + 1);
    }

    @Override
    public int save(Post post) {
     int result = 0;

     posts.put(count++, post);
     result = 1;
     return result;
    }

    @Override
    public int modify (int index, String title, String content) {
        int result = 0;

        if (title.isEmpty() && content.isEmpty()) {
            return result;
        }

        if (!title.isEmpty()) {
            posts.get(index).setTitle(title);
        }
        if (!content.isEmpty()) {
            posts.get(index).setContent(content);
        }

        result = 1;
        return result;
    }

    @Override
    public int remove(int postIndex) {
        int result = 0;

        if (posts.get(postIndex) == null) {
            return result;
        }

        posts.remove(postIndex);
        result = 1;

        return result;
    }

    @Override
    public TreeMap<Integer, Post> getCurUserPost (UUID userIndex) {
        Set<Integer> set = posts.keySet();
        TreeMap<Integer, Post> result = new TreeMap<>();

        for (Integer index : set) {
            if(posts.get(index).getOwner().equals(userIndex)) {
                result.put(index, posts.get(index));
            }
        }

        return result;
    }
}
