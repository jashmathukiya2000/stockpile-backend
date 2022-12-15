package com.example.auth.model;

import com.example.auth.decorator.Result;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "auth")
public class User {
    String id;
    String firstName;
    String middleName;
    String lastName;
    String fullName;
    double age;
    String occupation;
    double salary;
    Address address;
    String email;
    String phoneNumber;
    @JsonIgnore
    boolean softDelete = false;
    List<Result> result;
    double cgpa;

    public void setFullName() {
        this.firstName = StringUtils.normalizeSpace(this.firstName);
        this.middleName = StringUtils.normalizeSpace(this.middleName);
        this.lastName = StringUtils.normalizeSpace(this.lastName);

        List<String> fullNameList = new ArrayList<>();
        fullNameList.add(firstName);
        fullNameList.add(middleName);
        fullNameList.add(lastName);
        String string = fullNameList.stream().filter(Objects::nonNull).map(String::trim).collect(Collectors.joining(" "));
        this.fullName = string;
        String[] names = string.split(" ");
        if (names.length == 1) {
            this.firstName = names[0];
        } else if (names.length == 2) {
            this.firstName = names[0];
            this.lastName = names[0];

        } else if (names.length == 3) {
            this.firstName = names[0];
            this.middleName = names[1];
            this.lastName = names[2];
        } else if (names.length > 3) {
            this.firstName = names[0];
            this.middleName = names[1];
            StringBuilder stringBuilder = new StringBuilder();
            for (String value : names) {
                if (!value.equals(firstName) && !value.equals(lastName)) {
                    stringBuilder.append(" ").append(value);
                }
            }
            this.lastName = names.toString().trim();
        }
    }
}
