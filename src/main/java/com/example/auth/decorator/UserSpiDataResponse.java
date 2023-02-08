package com.example.auth.decorator;

import com.example.auth.commons.decorator.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSpiDataResponse {
    String _id;
    int sum;
    int count;
    int average;


    @ExcelField(excelHeader = "Id",position = 2)
    public String getId() {
        return _id;
    }
    @ExcelField(excelHeader = "Sum",position = 6)
    public double getSum(){
        return sum;

    }@ExcelField(excelHeader = "    Count",position = 7)
    public double getCount(){
        return count;

    }@ExcelField(excelHeader = "average",position = 8)
    public int getAverage(){
        return average;

    }

}
