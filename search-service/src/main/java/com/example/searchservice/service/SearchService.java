package com.example.searchservice.service;

import com.example.searchservice.model.SearchResultModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchService {

    private final static String SEARCH_Q = "q";
    private final static String SEARCH_TYPE = "type";

    public List<SearchResultModel> search(Map<String, String> params) {
        if (!params.containsKey(SEARCH_Q) || !params.containsKey(SEARCH_TYPE)) {
            throw new RuntimeException("Search params are wrong.");
        }

    }
}
