package com.simsns.ver01;

import java.util.Vector;

public class Post {
    private String title;
    private String content;
    private final User owner;
    private int likes;

    Vector<Comment> comments = new Vector<Comment>();



    public Post(String title, String content, User owner) {
        this.title = title;
        this.content = content;
        this.owner = owner;
    }

    @Override
    public String toString() {
        return String.format("%s | %s | ♥\uFE0F : %d | 작성자 : %s", title, content.substring(0,9) + "...", likes, owner);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getOwner() {
        return owner;
    }

    public int getLikes() {
        return likes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setLikes(int likes) {
        this.likes = likes;
    }

    public  Vector<Comment> getComments () {
        return comments;
    }

}
