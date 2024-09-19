package xyz.toway.bookservice.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookModel(Long id, @NotNull Long authorId, @NotNull @Size(max = 500) String title, Integer edition, String[] tags) {
}
