package com.example.auth.decorator.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFilter {
    String search;
    String id;
    @JsonIgnore
    boolean softDelete =false;

    public String getSearch(){
        if (search!=null){
            return search.trim();
        }
        return search;
    }
}
