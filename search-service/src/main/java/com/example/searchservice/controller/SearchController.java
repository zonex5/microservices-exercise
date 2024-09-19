package com.example.searchservice.controller;

import com.example.searchservice.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(@Autowired SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping
    private ResponseEntity<?> search(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(searchService.search(params));
    }
}
