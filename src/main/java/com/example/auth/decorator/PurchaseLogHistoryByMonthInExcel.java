package com.example.auth.decorator;

import com.example.auth.commons.decorator.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseLogHistoryByMonthInExcel {
    String id;
    String customerId;
    String customerName;

    String itemName;

    double price;

    double discountInPercent;

    double discountInRupee;

    double totalPrice;

    int quantity;


    Date date;

    @ExcelField(excelHeader = "ID",position = 2)
    public String getId(){
        return id;

    } @ExcelField(excelHeader = "CustomerId",position = 3)
    public String getCustomerId(){
        return customerId;

    } @ExcelField(excelHeader = "CustomerName",position = 4)
    public String getCustomerName(){
        return customerName;

    } @ExcelField(excelHeader = "ItemName",position = 5)
    public String getItemName(){
        return itemName;

    } @ExcelField(excelHeader = "Price",position = 6)
    public double getPrice(){
        return price;

    } @ExcelField(excelHeader = "DiscountInPercent",position = 7)
    public double getDiscountInPercent(){
        return discountInPercent;

    } @ExcelField(excelHeader = "DiscountInRupee",position = 8)
    public double getDiscountInRupee(){
        return discountInRupee;

    } @ExcelField(excelHeader = "TotalPrice",position = 9)
    public double getTotalPrice(){
        return totalPrice;

    } @ExcelField(excelHeader = "Quantity",position = 10)
    public double getQuantity(){
        return quantity;

    }@ExcelField(excelHeader = "Date",position = 11)
    public Date getDate(){
        return date;

    }

}
