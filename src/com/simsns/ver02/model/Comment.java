package com.simsns.ver02.model;

import java.util.UUID;

public class Comment {

    private String comment;
    private final UUID owner;

    public Comment(UUID userIndex, String comment) {
        this.comment = comment;
        this.owner = userIndex;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UUID getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return this.comment;
    }
}

