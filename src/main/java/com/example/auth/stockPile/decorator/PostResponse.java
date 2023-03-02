package com.example.auth.stockPile.decorator;

import com.example.auth.stockPile.model.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    String id;

    String title;

    String templateContent;

    UserData postBy;

    Date createdOn;

    String stockInfo;

    String topicInfo;

}
