package xyz.toway.bookservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.toway.bookservice.entity.AuthorEntity;
import xyz.toway.bookservice.repository.AuthorRepository;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(@Autowired AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorEntity saveAuthor(@Valid AuthorEntity author) {
        return authorRepository.save(author);
    }

    public List<AuthorEntity> getAllAuthors() {
        return authorRepository.findAll(Sort.by("name"));
    }

    public AuthorEntity getAuthor(Long id) {
        return authorRepository.findById(id).orElseThrow();
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
