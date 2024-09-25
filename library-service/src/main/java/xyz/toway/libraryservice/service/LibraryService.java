package xyz.toway.libraryservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.LibraryStockEntity;
import xyz.toway.libraryservice.repository.LibraryRepository;
import xyz.toway.libraryservice.repository.LibraryStockRepository;
import xyz.toway.shared.exception.WrongParamsException;
import xyz.toway.shared.model.SharedLibraryModel;
import xyz.toway.shared.model.SharedLibraryStockModel;

import java.util.List;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final LibraryStockRepository libraryStockRepository;

    public LibraryService(@Autowired LibraryRepository libraryRepository, @Autowired LibraryStockRepository libraryStockRepository) {
        this.libraryRepository = libraryRepository;
        this.libraryStockRepository = libraryStockRepository;
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

    public LibraryEntity updateLibrary(LibraryEntity library, Long id) {
        var existingLibrary = libraryRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("No library with id=" + id));
        existingLibrary.setName(library.getName());
        existingLibrary.setAddress(library.getAddress());
        return libraryRepository.save(existingLibrary);
    }

    public List<SharedLibraryModel> getAllLibrariesByBookIds(List<Long> ids) {
        return libraryStockRepository.findDistinctByBookIdInAndQuantityGreaterThan(ids, 0)
                .stream()
                .map(this::createLibraryModel)
                .distinct()
                .toList();
    }

    public List<SharedLibraryStockModel> getAllLibrariesStockByIds(List<Long> ids) {
        return libraryStockRepository.findDistinctByBookIdInAndQuantityGreaterThan(ids, 0)
                .stream()
                .map(this::createLibraryStockModel)
                .toList();
    }

    public LibraryEntity getLibraryById(Long id) {
        return libraryRepository.findById(id)
                .orElseThrow(() -> new WrongParamsException("No library with id=" + id));
    }

    private SharedLibraryModel createLibraryModel(LibraryStockEntity entity) {
        return new SharedLibraryModel(entity.getLibraryId(), entity.getName(), entity.getAddress());
    }

    private SharedLibraryStockModel createLibraryStockModel(LibraryStockEntity entity) {
        return new SharedLibraryStockModel(
                entity.getLibraryId(),
                entity.getName(),
                entity.getAddress(),
                entity.getId(),
                entity.getBookId(),
                entity.getQuantity(),
                entity.getPrice()
        );
    }
}
