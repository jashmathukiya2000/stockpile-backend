package com.example.auth.stockPile.repository;

import com.example.auth.decorator.pagination.FilterSortRequest;
import com.example.auth.stockPile.decorator.TitleResponse;
import com.example.auth.stockPile.decorator.TopicFilter;
import com.example.auth.stockPile.decorator.TopicResponse;
import com.example.auth.stockPile.decorator.TopicSortBy;
import org.json.JSONException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TopicCustomRepository {

    List<TitleResponse> getTopicIdByTitleAndDate(String createdOn, String title) throws JSONException;

    Page<TopicResponse> getAllTopicByPagination(TopicFilter filter, FilterSortRequest.SortRequest<TopicSortBy> sort, PageRequest pagination);
}
