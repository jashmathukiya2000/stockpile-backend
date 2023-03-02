package com.example.auth.stockPile.decorator;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum PostSortBy {
    DATE("createdOn"),
  CONTENT("templateContent");

    @JsonIgnore
    private String value;

    PostSortBy(String value) {
        this.value = value;
    }
}
