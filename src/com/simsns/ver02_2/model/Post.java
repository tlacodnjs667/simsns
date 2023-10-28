package com.simsns.ver02_2.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Post implements Serializable {

    private String title;
    private String content;
    private final UUID owner;

    private int likes;

    ArrayList<Comment> comments = new ArrayList<>();

    public Post(String title, String content, UUID owner) {
        this.title = title;
        this.content = content;
        this.owner = owner;
    }

    @Override
    public String toString() {

        return String.format("%s | %s | ♥\uFE0F : %d | 작성자 : %s", title, content.substring(0,content.length() > 9 ? 9 : content.length() - 1) + "...", likes, owner);
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public  ArrayList<Comment> getComments () {
        return comments;
    }



}
