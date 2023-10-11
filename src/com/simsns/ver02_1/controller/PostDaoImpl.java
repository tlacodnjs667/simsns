package com.simsns.ver02_1.controller;

import com.simsns.ver02_1.model.Post;

import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

public class PostDaoImpl implements PostDao {

    private static PostDaoImpl instance = new PostDaoImpl();
    private final TreeMap<Integer, Post> posts;
    private static int count = 0;

    private PostDaoImpl () {
        this.posts = DataUtil.getPosts() != null ? DataUtil.getPosts() : new TreeMap<>();
    }

    public static PostDaoImpl getInstance () {
        if (instance == null)
        {
            instance = new PostDaoImpl();
        }

        return  instance;
    }

    @Override
    public TreeMap<Integer, Post> get ()  {
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
        DataUtil.reservePosts(posts);
    }

    @Override
    public int save(Post post) {
     int result = 0;

     posts.put(count++, post);
     DataUtil.reservePosts(posts);
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

        DataUtil.reservePosts(this.posts);

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

        DataUtil.reservePosts(this.posts);

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
