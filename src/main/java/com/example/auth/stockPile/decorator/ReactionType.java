package com.example.auth.stockPile.decorator;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ReactionType {
    UPVOTE("upVote"),
    DOWNVOTE("downVote");

    private String value;

    ReactionType(String value) {
        this.value = value;
    }
}
