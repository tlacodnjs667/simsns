package com.simsns.ver02_1.model;

import java.io.Serializable;
import java.util.UUID;

public class Comment implements Serializable {

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

