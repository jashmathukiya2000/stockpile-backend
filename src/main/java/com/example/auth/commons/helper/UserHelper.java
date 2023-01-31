package com.example.auth.commons.helper;

import com.example.auth.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHelper {
    String firstName;
    String middleName;
    String lastName;
    String fullName;
    public String getFullName(User user) {
        this.firstName = StringUtils.normalizeSpace(user.getFirstName());
        this.middleName = StringUtils.normalizeSpace(user.getMiddleName());
        this.lastName = StringUtils.normalizeSpace(user.getLastName());

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
        return string;
    }



}
