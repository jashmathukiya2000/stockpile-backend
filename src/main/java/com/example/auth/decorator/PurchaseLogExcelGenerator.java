package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseLogExcelGenerator extends HashMap<String, List<PurchaseLogExcelGenerator>> {
    String date;
    String itemName;
    double count;
    double totalPrice;

    @ExcelField(excelHeader = "Date", position=2)
    public String getDate(){
        return date;
    }


    @ExcelField(excelHeader = "ItemName", position=3)
    public String getItemName(){
        return itemName;
    }
      @ExcelField(excelHeader = "Count", position=4)
    public double getCount(){
        return count;
    }

      @ExcelField(excelHeader = "TotalPrice", position=5)

    public double getTotalPrice() {
        return totalPrice;
    }


}