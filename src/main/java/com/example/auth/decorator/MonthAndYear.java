package com.example.auth.decorator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthAndYear {
    List<UserDateDetails> userDateDetails ;
    Set<String> title;
    int totalCount;
}
