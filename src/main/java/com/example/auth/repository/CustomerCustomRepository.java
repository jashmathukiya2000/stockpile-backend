package com.example.auth.repository;

import com.example.auth.decorator.pagination.CustomerFilter;
import com.example.auth.decorator.pagination.CustomerSortBy;
import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CustomerCustomRepository {

    Page<Customer> getAllCustomerByPagination(CustomerFilter filter, FilterSortRequest.SortRequest<CustomerSortBy> sort, PageRequest pagination);

}
