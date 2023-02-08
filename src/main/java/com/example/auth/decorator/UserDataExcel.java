package com.example.auth.decorator;

import com.example.auth.commons.decorator.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataExcel {
    double salary;
    String phoneNumber;
    String email;

    @ExcelField(excelHeader = "Salary", position = 2)
    public double getSalary() {
        return salary;
    }

    @ExcelField(excelHeader = "PhoneNumber", position = 3)
    public String getPhoneNum() {
        return phoneNumber;
    }

    @ExcelField(excelHeader = "Email", position = 4)
    public String getEmail() {
        return email;
    }

}


