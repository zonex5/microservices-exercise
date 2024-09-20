package xyz.toway.libraryservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Data
@Entity
@Immutable
@Table(name = "libraries_view")
public class LibraryStockEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "id_stock")
    private Long stockId;

    @Column(name = "id_book")
    private Long bookId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;
}
