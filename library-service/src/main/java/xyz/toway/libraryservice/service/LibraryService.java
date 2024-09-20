package xyz.toway.libraryservice.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.LibraryStockEntity;
import xyz.toway.libraryservice.repository.LibraryRepository;
import xyz.toway.libraryservice.repository.LibraryStockRepository;
import xyz.toway.shared.model.SharedLibraryModel;
import xyz.toway.shared.model.SharedLibraryStockModel;

import java.util.List;
import java.util.Optional;

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

    public Optional<LibraryEntity> updateLibrary(LibraryEntity library, Long id) {
        return libraryRepository.findById(id)
                .map(entity -> {
                    entity.setName(library.getName());
                    entity.setAddress(library.getAddress());
                    return entity;
                })
                .map(libraryRepository::save);
    }

    public List<SharedLibraryModel> getAllLibrariesByBookIds(List<Long> ids) {
        return libraryStockRepository.findAllByBookIdInAndQuantityGreaterThan(ids, 0)
                .stream()
                .map(this::createLibraryModel)
                .toList();
    }

    public List<SharedLibraryStockModel> getAllLibrariesStockByIds(List<Long> ids) {
        return libraryStockRepository.findAllByBookIdInAndQuantityGreaterThan(ids, 0)
                .stream()
                .map(this::createLibraryStockModel)
                .toList();
    }

    public SharedLibraryModel createLibraryModel(LibraryStockEntity entity) {
        return new SharedLibraryModel(entity.getId(), entity.getName(), entity.getAddress());
    }

    public SharedLibraryStockModel createLibraryStockModel(LibraryStockEntity entity) {
        return new SharedLibraryStockModel(entity.getId(), entity.getName(), entity.getAddress(), entity.getStockId(), entity.getBookId(), entity.getQuantity(), entity.getPrice());
    }
}
