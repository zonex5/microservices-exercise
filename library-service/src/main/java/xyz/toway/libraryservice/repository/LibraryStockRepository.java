package xyz.toway.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.libraryservice.entity.LibraryStockEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryStockRepository extends JpaRepository<LibraryStockEntity, Long> {

    List<LibraryStockEntity> findDistinctByBookIdInAndQuantityGreaterThan(List<Long> ids, Integer quantity);

    Optional<LibraryStockEntity> findFirstByLibraryIdAndBookId(Long id, Long bookId);

    boolean existsByBookId(Long bookId);
}
