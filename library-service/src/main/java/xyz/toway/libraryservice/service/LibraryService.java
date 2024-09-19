package xyz.toway.libraryservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.repository.LibraryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryService(@Autowired LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public LibraryEntity saveLibrary(@Valid LibraryEntity library) {
        return libraryRepository.save(library);
    }

    public List<LibraryEntity> getAllLibraries() {
        return libraryRepository.findAll(Sort.by("name"));
    }

    public void deleteLibrary(Long id) {
        libraryRepository.deleteById(id);
    }

    public Optional<LibraryEntity> updateLibrary(LibraryEntity library, Long id) {
        return libraryRepository.findById(id)
                .map(entity -> {
                    entity.setName(library.getName());
                    entity.setAddress(library.getAddress());
                    return entity;
                })
                .map(libraryRepository::save);
    }
}
