package xyz.toway.bookservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.bookservice.entity.AuthorEntity;
import xyz.toway.bookservice.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(@Autowired AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    private ResponseEntity<?> getAllAuthors() {
        List<AuthorEntity> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @PostMapping
    private ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorEntity author) {
        AuthorEntity createdAuthor = authorService.saveAuthor(author);
        return ResponseEntity.ok(createdAuthor);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateAuthor(@Valid @RequestBody AuthorEntity author, @PathVariable Long id) {
        AuthorEntity updatedAuthor = authorService.updateAuthor(author, id);
        return ResponseEntity.ok(updatedAuthor);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }
}
