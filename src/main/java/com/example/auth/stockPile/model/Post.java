package com.example.auth.stockPile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "posts")
public class Post {
    @Id
    String id;

    String content;

    UserData postBy;

    Date createdOn;

    String stockInfo;

    String topicInfo;

    @JsonIgnore
    boolean softDelete;


}
