package com.example.auth.commons.model;

import com.amazonaws.services.dynamodbv2.xspec.S;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class EmailModel {
    String to;
    String subject;
    String message;
    String templateName;
    Set<String> bcc;
    Set<String> cc;
    File file;

}
