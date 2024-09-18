package xyz.toway.bookservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import xyz.toway.bookservice.entity.BookEntity;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "SELECT * FROM public.books WHERE :tag ILIKE ANY (tags)", nativeQuery = true)
    List<BookEntity> findByTag(String tag);

    List<BookEntity> findByTitleIsContainingIgnoreCase(String title);

    List<BookEntity> findByAuthorNameIsContainingIgnoreCase(String name);
}
