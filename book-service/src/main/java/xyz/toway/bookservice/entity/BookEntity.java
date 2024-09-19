package xyz.toway.bookservice.entity;

import io.hypersistence.utils.hibernate.type.array.StringArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

@Data
@Entity
@Table(name = "books")
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "edition")
    private Integer edition;

    @Type(StringArrayType.class)
    @Column(name = "tags", columnDefinition = "text[]")
    private String[] tags;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_author", nullable = false)
    private AuthorEntity author;

}