package com.simsns.ver02.controller;

import com.simsns.ver02.model.Comment;

import java.util.UUID;

public class CommentDaoImpl implements CommentDao{
    PostDaoImpl postDao = PostDaoImpl.getInstance();

    private static CommentDaoImpl instance;

    private CommentDaoImpl () {}

    public static CommentDaoImpl getInstance() {
        if (instance == null) instance = new CommentDaoImpl();
        return instance;
    }


    @Override
    public int save(UUID userIndex, int postIndex, String content) {
        int result = 0;

        postDao.get(postIndex).getComments().add(new Comment(userIndex, content));
        result = 1;

        return result;
    }
}
