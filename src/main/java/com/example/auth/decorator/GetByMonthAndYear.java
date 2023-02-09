package com.example.auth.decorator;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetByMonthAndYear {
    Id _id;
    List<ItemDetails> itemDetail;
    int totalItem;


}

