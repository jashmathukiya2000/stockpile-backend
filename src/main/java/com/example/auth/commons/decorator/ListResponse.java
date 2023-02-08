package com.example.auth.commons.decorator;

import com.example.auth.commons.decorator.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResponse<T> {
    List<T> data;
    Response status;
}
