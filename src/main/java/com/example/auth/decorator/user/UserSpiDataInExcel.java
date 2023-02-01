package com.example.auth.decorator.user;

import com.example.auth.decorator.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSpiDataInExcel {
    String _id;
    String name;
    String email;
    String semester;


    @ExcelField(excelHeader = "Id",position = 2)
    public String getId(){
        return _id;

    }  @ExcelField(excelHeader = "Name",position = 3)
    public String getName(){
        return name;

    }  @ExcelField(excelHeader = "Email",position = 4)
    public String getEmail(){
        return email;

    }  @ExcelField(excelHeader = "Semester",position = 5)
    public String getSemester(){
        return semester;

    }

}
