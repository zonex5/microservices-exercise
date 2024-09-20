package xyz.toway.bookservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.model.BookModel;
import xyz.toway.bookservice.service.BookService;
import xyz.toway.shared.model.SharedBookModel;

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
        List<SharedBookModel> books = bookService.searchBooks(params);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search-ids")
    public ResponseEntity<?> searchBooksIds(@RequestParam Map<String, String> params) {
        List<Long> ids = bookService.searchBooksIds(params);
        return ResponseEntity.ok(ids);
    }

    @PostMapping
    private ResponseEntity<?> addBook(@Valid @RequestBody BookModel book) {
        try {
            BookEntity bookEntity = bookService.saveBook(book);
            return ResponseEntity.ok(bookEntity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBook(@Valid @RequestBody BookModel book, @PathVariable Long id) {
        try {
            BookEntity bookEntity = bookService.updateBook(book, id);
            return ResponseEntity.ok(bookEntity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @GetMapping("/exists/{id}")
    private ResponseEntity<?> chekBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.bookExists(id));
    }
}
