package xyz.toway.shared.model;

import java.time.LocalDate;

public record SharedAuthorModel(Long id, String name, LocalDate dateOfBirth, LocalDate dateOfDeath) {
}
