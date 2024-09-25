package xyz.toway.bookservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.model.BookModel;
import xyz.toway.bookservice.service.BookService;
import xyz.toway.shared.exception.WrongParamsException;
import xyz.toway.shared.model.SharedBookModel;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final String GENERAL_ERROR = "Something went wrong.";

    private final BookService bookService;

    public BookController(@Autowired BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        try {
            List<BookEntity> users = bookService.getAllBooks();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchBooks(@RequestParam Map<String, String> params) {
        try {
            List<SharedBookModel> books = bookService.searchBooks(params);
            return ResponseEntity.ok(books);
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/search-ids")
    public ResponseEntity<?> searchBooksIds(@RequestParam Map<String, String> params) {
        try {
            List<Long> ids = bookService.searchBooksIds(params);
            return ResponseEntity.ok(ids);
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @PostMapping
    private ResponseEntity<?> addBook(@Valid @RequestBody BookModel book) {
        try {
            BookEntity bookEntity = bookService.saveBook(book);
            return ResponseEntity.ok(bookEntity);
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<?> updateBook(@Valid @RequestBody BookModel book, @PathVariable Long id) {
        try {
            BookEntity bookEntity = bookService.updateBook(book, id);
            return ResponseEntity.ok(bookEntity);
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<?> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.getBook(id));
        } catch (WrongParamsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(GENERAL_ERROR);
        }
    }

    @GetMapping("/exists/{id}")
    private ResponseEntity<?> checkBook(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(bookService.bookExists(id));
        } catch (Exception e) {
            return ResponseEntity.ok(false);
        }
    }
}
