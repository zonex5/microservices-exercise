package xyz.toway.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.LibraryStockEntity;

import java.util.List;

@Repository
public interface LibraryStockRepository extends JpaRepository<LibraryStockEntity, Long> {

    List<LibraryStockEntity> findAllByBookIdInAndQuantityGreaterThan(List<Long> ids, Integer quantity);
}
