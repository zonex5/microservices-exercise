package xyz.toway.bookservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.toway.bookservice.entity.BookEntity;
import xyz.toway.bookservice.model.BookModel;
import xyz.toway.bookservice.repository.AuthorRepository;
import xyz.toway.bookservice.repository.BookRepository;
import xyz.toway.shared.model.SharedAuthorModel;
import xyz.toway.shared.model.SharedBookModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class BookService {

    private final static String SEARCH_Q = "q";
    private final static String SEARCH_TYPE = "type";

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

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<SharedBookModel> searchBooks(Map<String, String> params) {
        checkQueryParams(params);
        String q = params.get(SEARCH_Q);
        return switch (params.get(SEARCH_TYPE)) {
            case "tag" -> bookRepository.findByTag(q)
                    .stream()
                    .map(this::createSharedBookModel)
                    .toList();
            case "title" -> bookRepository.findByTitleIsContainingIgnoreCase(q)
                    .stream()
                    .map(this::createSharedBookModel)
                    .toList();
            case "author" -> bookRepository.findByAuthorNameIsContainingIgnoreCase(q)
                    .stream()
                    .map(this::createSharedBookModel)
                    .toList();
            default -> new ArrayList<>();
        };
    }

    public List<Long> searchBooksIds(Map<String, String> params) {
        checkQueryParams(params);
        String q = params.get(SEARCH_Q);
        return switch (params.get(SEARCH_TYPE)) {
            case "tag" -> bookRepository.findByTag(q)
                    .stream()
                    .map(BookEntity::getId)
                    .toList();
            case "title" -> bookRepository.findByTitleIsContainingIgnoreCase(q)
                    .stream()
                    .map(BookEntity::getId)
                    .toList();
            case "author" -> bookRepository.findByAuthorNameIsContainingIgnoreCase(q)
                    .stream()
                    .map(BookEntity::getId)
                    .toList();
            default -> new ArrayList<>();
        };
    }

    public BookEntity getBook(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public boolean bookExists(Long id) {
        return bookRepository.existsById(id);
    }

    private SharedBookModel createSharedBookModel(BookEntity entity) {

        if (Objects.isNull(entity)) return null;

        SharedAuthorModel authorModel = null;
        var author = entity.getAuthor();
        if (Objects.nonNull(author)) {
            authorModel = new SharedAuthorModel(author.getId(), author.getName(), author.getDateOfBirth(), author.getDateOfDeath());
        }
        return new SharedBookModel(
                entity.getId(),
                entity.getTitle(),
                entity.getEdition(),
                entity.getTags(),
                authorModel
        );
    }

    private BookEntity createBookEntity(BookModel model) {
        BookEntity entity = new BookEntity();
        entity.setId(model.id());
        entity.setTitle(model.title());
        entity.setTags(model.tags());
        entity.setEdition(model.edition());
        return entity;
    }

    private static void checkQueryParams(Map<String, String> params) {
        if (params == null || !params.containsKey(SEARCH_Q) || !params.containsKey(SEARCH_TYPE)) {
            throw new RuntimeException("The search parameters are incorrect.");
        }
    }
}
