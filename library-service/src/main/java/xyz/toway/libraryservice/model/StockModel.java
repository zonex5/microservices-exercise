package xyz.toway.libraryservice.model;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record StockModel(Long id, @NotNull Long libraryId, @NotNull Long bookId, @NotNull Integer quantity, @NotNull BigDecimal price) {
}
