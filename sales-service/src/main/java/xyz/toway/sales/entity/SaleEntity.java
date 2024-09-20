package xyz.toway.sales.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "sales")
@NoArgsConstructor
@AllArgsConstructor
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_library", nullable = false)
    @NotNull(message = "id_library must not be null.")
    Long libraryId;

    @Column(name = "id_book", nullable = false)
    @NotNull(message = "id_book must not be null.")
    Long bookId;

    @Column(name = "quantity", nullable = false)
    @PositiveOrZero
    Integer quantity;

    @Column(name = "sale_date", nullable = false)
    LocalDateTime saleDate;
}
