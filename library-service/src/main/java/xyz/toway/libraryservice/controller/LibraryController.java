package xyz.toway.libraryservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.service.LibraryService;

import java.util.List;

@RestController
@RequestMapping("/libraries")
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(@Autowired LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    private ResponseEntity<?> getAllLibraries() {
        List<LibraryEntity> libs = libraryService.getAllLibraries();
        return ResponseEntity.ok(libs);
    }

    @PostMapping
    private ResponseEntity<?> addLibrary(@Valid @RequestBody LibraryEntity library) {
        try {
            LibraryEntity libraryEntity = libraryService.saveLibrary(library);
            return ResponseEntity.ok(libraryEntity);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body("The specified name already exists.");
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBook(@Valid @RequestBody LibraryEntity library, @PathVariable Long id) {
        var libraryOptional = libraryService.updateLibrary(library, id);
        if (libraryOptional.isPresent()) {
            return ResponseEntity.ok(libraryOptional.get());
        }
        return ResponseEntity.badRequest().body("The library with id=" + id + " does not exists.");
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBook(@PathVariable Long id) {
        libraryService.deleteLibrary(id);
        return ResponseEntity.ok().build();
    }
}
