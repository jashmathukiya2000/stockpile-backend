package com.example.auth.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;


@Document(collection= "admin_config")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@Builder
public class AdminConfiguration {

    @Id
    String id;
    String from;
    String username;
    String password;
    String host;
    String port;
    String CreatedBy;
    String UpdatedBy;
    Date Created;
    Date Updated;
    boolean smutAuth;
    boolean starttls;
}