package com.example.auth.decorator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResponse {
    String categoryId;

    String id;

    String itemName;

    double price;

    int quantity;

    double discountInPercent;

    double discountInRupee;

    double totalPrice;
    Date date;


}
