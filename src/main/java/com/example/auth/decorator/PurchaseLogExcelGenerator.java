package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseLogExcelGenerator {

    String itemName;

    double count;

    double totalPrice;

    double totalItem;

    @ExcelField(excelHeader = "ItemName", position=1)
    public String getItemName(){
        return itemName;
    }
      @ExcelField(excelHeader = "Count", position=2)
    public double getCount(){
        return count;
    }
      @ExcelField(excelHeader = "TotalPrice", position=3)
    public double getTotalPrice(){
        return totalPrice;
    }

    @ExcelField(excelHeader = "TotalItem", position=4)
    public double getTotalItem(){
        return totalItem;
    }


}