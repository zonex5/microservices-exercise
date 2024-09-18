package xyz.toway.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.toway.bookservice.entity.BookEntity;

@Repository
public interface BooksRepository extends JpaRepository<BookEntity, Long> {
}
