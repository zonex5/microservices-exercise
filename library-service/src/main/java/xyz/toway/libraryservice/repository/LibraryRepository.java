package xyz.toway.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.libraryservice.entity.LibraryEntity;

@Repository
public interface LibraryRepository extends JpaRepository<LibraryEntity, Long> {
}
