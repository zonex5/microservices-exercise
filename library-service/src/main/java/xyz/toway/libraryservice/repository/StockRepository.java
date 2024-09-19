package xyz.toway.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.libraryservice.entity.LibraryEntity;
import xyz.toway.libraryservice.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
}
