package xyz.toway.shared.model;

import java.math.BigDecimal;

public record SharedLibraryStockModel(Long id, String name, String address, Long stockId, Long bookId, Integer quantity, BigDecimal price) {
}
