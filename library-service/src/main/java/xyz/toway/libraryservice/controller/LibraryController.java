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

    private final String GENERAL_ERROR = "Something went wrong.";

    private final static int MAX_ALLOWED_IDS = 100;

    private final LibraryService libraryService;

    public LibraryController(@Autowired LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    private ResponseEntity<?> getAllLibraries() {
        try {
            return ResponseEntity.ok(libraryService.getAllLibraries());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getLibraryById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(libraryService.getLibraryById(id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/search-by-book-ids")
    private ResponseEntity<?> getAllLibrariesByBookIds(@RequestParam("id") List<Long> ids) {
        if (ids.size() > MAX_ALLOWED_IDS) {
            return ResponseEntity.badRequest().body("Too many books match the specified search criteria. Please try to refine the criteria.");
        }
        return ResponseEntity.ok(libraryService.getAllLibrariesByBookIds(ids));
    }

    @GetMapping("/search-stock-by-ids")
    private ResponseEntity<?> getAllLibrariesStockByIds(@RequestParam("id") List<Long> ids) {
        if (ids.size() > MAX_ALLOWED_IDS) {
            return ResponseEntity.badRequest().body("Too many books match the specified search criteria. Please try to refine the criteria.");
        }
        return ResponseEntity.ok(libraryService.getAllLibrariesStockByIds(ids));
    }

    @PostMapping
    private ResponseEntity<?> addLibrary(@Valid @RequestBody LibraryEntity library) {
        try {
            return ResponseEntity.ok(libraryService.saveLibrary(library));
        } catch (DataIntegrityViolationException e) {
            log.error(e);
            return ResponseEntity.badRequest().body("The specified name already exists.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateLibrary(@Valid @RequestBody LibraryEntity library, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(libraryService.updateLibrary(library, id));
        } catch (WrongParamsException e) {
            log.error(e);
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteLibrary(@PathVariable Long id) {
        try {
            libraryService.deleteLibrary(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }
}
