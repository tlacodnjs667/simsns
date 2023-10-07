package com.simsns.ver01;

public class Comment {

    private String comment;
    private final User owner;

    public Comment(String comment, User owner) {
        this.comment = comment;
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return owner.getId() +" : " + this.comment;
    }
}
