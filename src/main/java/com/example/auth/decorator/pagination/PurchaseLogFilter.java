package com.example.auth.decorator.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseLogFilter {
    String search;
    Set<String> ids;
    int month;
    int year;

    @JsonIgnore
    boolean softDelete;


    public String getSearch() {
        if (search != null) {
            return search.trim();
        }
        return search;
    }


}
