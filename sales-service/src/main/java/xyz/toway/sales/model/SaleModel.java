package xyz.toway.sales.model;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SaleModel(Long id,
                        @NotNull Long libraryId,
                        @NotNull Long bookId,
                        @NotNull Integer quantity,
                        LocalDateTime saleDate,
                        String instanceId
) {
}
