package xyz.toway.bookservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.bookservice.entity.AuthorEntity;
import xyz.toway.bookservice.service.AuthorService;
import xyz.toway.shared.exception.WrongParamsException;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final String GENERAL_ERROR = "Something went wrong.";

    private final AuthorService authorService;

    public AuthorController(@Autowired AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    private ResponseEntity<?> getAllAuthors() {
        try {
            List<AuthorEntity> authors = authorService.getAllAuthors();
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getAuthor(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(authorService.getAuthor(id));
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GENERAL_ERROR);
        }
    }

    @PostMapping
    private ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorEntity author) {
        try {
            AuthorEntity createdAuthor = authorService.saveAuthor(author);
            return ResponseEntity.ok(createdAuthor);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GENERAL_ERROR);
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateAuthor(@Valid @RequestBody AuthorEntity author, @PathVariable Long id) {
        try {
            AuthorEntity updatedAuthor = authorService.updateAuthor(author, id);
            return ResponseEntity.ok(updatedAuthor);
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GENERAL_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(GENERAL_ERROR);
        }
    }
}
