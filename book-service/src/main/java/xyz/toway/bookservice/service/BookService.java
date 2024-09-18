package xyz.toway.bookservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.repository.BookRepository;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookEntity saveBook(@Valid BookEntity author) {
        return bookRepository.save(author);
    }

    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll(Sort.by("title"));
    }

    public List<BookEntity> getAllByTag(String tag) {
        return bookRepository.findByTag(tag);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<BookEntity> searchBooks(Map<String, String> params) {
        if (params == null || params.isEmpty()) return null;

        // search by tag
        if (params.containsKey("tag")) {
            return bookRepository.findByTag(params.get("tag"));
        }
        // search by title
        else if (params.containsKey("title")) {
            return bookRepository.findByTitleIsContainingIgnoreCase(params.get("title"));
        }
        // search by author
        else if (params.containsKey("author")) {
            return bookRepository.findByAuthorNameIsContainingIgnoreCase(params.get("author"));
        }

        return null;
    }
}
