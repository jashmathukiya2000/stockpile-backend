package com.example.auth.stockPile.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "topics")

public class Topic {

    String id;

    String title;

    String description;

    String stocks;

    String createdBy;

    Date createdOn;

    @JsonIgnore

    boolean softDelete;

}
