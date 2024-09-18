package xyz.toway.bookservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@NoArgsConstructor
@AllArgsConstructor
public class AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "The name must not be empty.")
    private String name;

    @Column(name = "date_of_birth")
    @NotNull(message = "The date of birth is required.")
    @PastOrPresent(message = "The date of birth cannot be in the future.")
    private LocalDate dateOfBirth;

    @Column(name = "date_of_death")
    @PastOrPresent(message = "The date of death cannot be in the future.")
    private LocalDate dateOfDeath;
}
