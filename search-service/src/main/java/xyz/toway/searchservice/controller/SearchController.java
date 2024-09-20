package xyz.toway.searchservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.toway.searchservice.service.SearchService;
import xyz.toway.shared.exception.WrongParamsException;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(@Autowired SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/books")
    private ResponseEntity<?> searchBooks(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(searchService.searchBooks(params));
    }

    @GetMapping("/libraries")
    private ResponseEntity<?> searchLibraries(@RequestParam Map<String, String> params) {
        try {
            return ResponseEntity.ok(searchService.searchLibraries(params));
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
