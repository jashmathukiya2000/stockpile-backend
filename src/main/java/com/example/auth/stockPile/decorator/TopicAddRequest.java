package com.example.auth.stockPile.decorator;


import com.example.auth.stockPile.model.UserData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicAddRequest {

    String title;

    String description;

//    UserData createdBy;



}
