package xyz.toway.bookservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.service.BookService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<BookEntity> users = bookService.getAllBooks();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam Map<String, String> params) {
        List<BookEntity> users = bookService.searchBooks(params);
        if (users == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

/*    @PostMapping
    private ResponseEntity<?> createAuthor(@Valid @RequestBody AuthorEntity author) {
        AuthorEntity createdUser = authorService.saveAuthor(author);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateAuthor(@Valid @RequestBody AuthorEntity author, @PathVariable Long id) {
        author.setId(id);
        AuthorEntity createdUser = authorService.saveAuthor(author);
        return ResponseEntity.ok(createdUser);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }*/
}
