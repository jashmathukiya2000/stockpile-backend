package com.example.auth.stockPile.decorator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    String id;
    String comment;
    String commentId;
    String commentedBy;
    Date createdOn;
    String post;
}
