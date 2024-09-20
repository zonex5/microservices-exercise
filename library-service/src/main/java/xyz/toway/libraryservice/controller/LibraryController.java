package xyz.toway.libraryservice.controller;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.service.LibraryService;
import xyz.toway.shared.exception.WrongParamsException;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/libraries")
public class LibraryController {

    private final static int MAX_ALLOWED_IDS = 100;

    private final LibraryService libraryService;

    public LibraryController(@Autowired LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    private ResponseEntity<?> getAllLibraries() {
        var libs = libraryService.getAllLibraries();
        return ResponseEntity.ok(libs);
    }

    @GetMapping("/search-by-book-ids")
    private ResponseEntity<?> getAllLibrariesByBookIds(@RequestParam("id") List<Long> ids) {
        if (ids.size() > MAX_ALLOWED_IDS) {
            return ResponseEntity.badRequest().body("Max allowed books to search: " + MAX_ALLOWED_IDS);
        }
        var items = libraryService.getAllLibrariesByBookIds(ids);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/search-stock-by-ids")
    private ResponseEntity<?> getAllLibrariesStockByIds(@RequestParam("id") List<Long> ids) {
        if (ids.size() > MAX_ALLOWED_IDS) {
            return ResponseEntity.badRequest().body("Max allowed books to search: " + MAX_ALLOWED_IDS);
        }
        var items = libraryService.getAllLibrariesStockByIds(ids);
        return ResponseEntity.ok(items);
    }

    @PostMapping
    private ResponseEntity<?> addLibrary(@Valid @RequestBody LibraryEntity library) {
        try {
            LibraryEntity libraryEntity = libraryService.saveLibrary(library);
            return ResponseEntity.ok(libraryEntity);
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            return ResponseEntity.badRequest().body("The specified name already exists.");
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateLibrary(@Valid @RequestBody LibraryEntity library, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(libraryService.updateLibrary(library, id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteLibrary(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
        return ResponseEntity.ok().build();
    }
}
