package com.simsns.ver02.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Vector;

public class Post {

    private UUID index = UUID.randomUUID(); // post 정보 내의 index

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
        return String.format("%s | %s | ♥\uFE0F : %d | 작성자 : %s", title, content.substring(0,9) + "...", likes, owner);
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

    public UUID getIndex() {return index;}

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
