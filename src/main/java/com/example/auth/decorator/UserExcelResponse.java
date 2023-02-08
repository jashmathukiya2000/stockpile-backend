package com.example.auth.decorator;

import com.example.auth.commons.decorator.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserExcelResponse {
    String firstName;
    String middleName;
    String lastName;
    String fullName;
    double age;
    String occupation;
    double salary;

    @ExcelField(excelHeader = "FirstName", position = 2)
    public String getFirstName() {
        return firstName;
    }

    @ExcelField(excelHeader = "MiddleName", position = 3)
    public String getMiddleName() {
        return middleName;
    }

    @ExcelField(excelHeader = "LastName", position = 4)
    public String getLastName() {
        return lastName;
    }

    @ExcelField(excelHeader = "FullName", position = 5)
    public String getFullName() {
        return fullName;
    }

    @ExcelField(excelHeader = "Age", position = 6)
    public double getAge() {
        return age;
    }

    @ExcelField(excelHeader = "Occupation", position = 7)
    public String getOccupation() {
        return occupation;
    }

    @ExcelField(excelHeader = "Salary", position = 8)
    public double getSalary() {
        return salary;
    }


}
