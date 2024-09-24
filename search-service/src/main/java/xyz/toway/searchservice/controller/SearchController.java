package xyz.toway.searchservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.toway.searchservice.service.SearchService;
import xyz.toway.shared.exception.WrongParamsException;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(@Autowired SearchService searchService) {
        this.searchService = searchService;
    }

    @Operation(summary = "Searching for books by specific parameters.",
            description = "The parameters are passed as a query string in the URL: ?q=<value to search>&by=[tag, title, author]")
    @GetMapping("/books")
    private ResponseEntity<?> searchBooks(@RequestParam Map<String, String> params) {
        try {
            return ResponseEntity.ok(searchService.searchBooks(params));
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Searching for libraries that contain books based on specific parameters.",
            description = "The parameters are passed as a query string in the URL: ?q=<value to search>&by=[tag, title, author]")
    @GetMapping("/libraries")
    private ResponseEntity<?> searchLibraries(@RequestParam Map<String, String> params) {
        try {
            return ResponseEntity.ok(searchService.searchLibraries(params));
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
