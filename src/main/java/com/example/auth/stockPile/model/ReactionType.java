package com.example.auth.stockPile.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ReactionType {
    UPVOTE("upVote"),
    DOWNVOTE("downVote");

      private String value;


    private int count;

    ReactionType(String value) {
        this.value = value;
        this.count = 0;
    }

    public void incrementCount() {
        this.count++;
    }}
