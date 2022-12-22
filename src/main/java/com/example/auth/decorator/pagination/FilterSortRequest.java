package com.example.auth.decorator.pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterSortRequest<FILTER, SORT> {
    FILTER filter;
    SortRequest<SORT> sort;
    Pagination pagination;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SortRequest<SORT> {
        SORT SortBy;
        Sort.Direction orderBy;
    }
}
