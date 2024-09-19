package xyz.toway.bookservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.model.BookModel;
import xyz.toway.bookservice.repository.AuthorRepository;
import xyz.toway.bookservice.repository.BookRepository;

import java.util.List;
import java.util.Map;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(@Autowired BookRepository bookRepository, @Autowired AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public BookEntity saveBook(@Valid BookEntity book) {
        return bookRepository.save(book);
    }

    public BookEntity saveBook(@Valid BookModel book) {
        var opt = authorRepository.findById(book.authorId());
        if (opt.isPresent()) {
            var bk = createBookEntity(book);
            bk.setAuthor(opt.get());
            return bookRepository.save(bk);
        } else {
            throw new RuntimeException("No author with id=" + book.authorId());
        }
    }

    public BookEntity updateBook(@Valid BookModel book, Long id) {
        var updatedBook = new BookModel(id, book.authorId(), book.title(), book.edition(), book.tags());
        return saveBook(updatedBook);
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

    private BookEntity createBookEntity(BookModel model) {
        BookEntity entity = new BookEntity();
        entity.setId(model.id());
        entity.setTitle(model.title());
        entity.setTags(model.tags());
        entity.setEdition(model.edition());
        return entity;
    }

    public BookEntity getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public boolean bookExists(Long id) {
        return bookRepository.existsById(id);
    }
}
