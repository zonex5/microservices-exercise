package xyz.toway.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.libraryservice.entity.StockEntity;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findByLibraryIdAndBookId(Long libraryId, Long bookId);
}
