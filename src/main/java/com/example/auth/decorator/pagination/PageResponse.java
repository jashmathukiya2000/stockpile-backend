package com.example.auth.decorator.pagination;

import com.example.auth.decorator.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    Page<T> data;
    Response status;


}
