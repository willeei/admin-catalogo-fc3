package br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get;

import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;

import java.time.Instant;
import java.util.List;

public record GenreOutput(
        String id,
        String name,
        boolean isActive,
        List<String> categories,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static GenreOutput from(Genre genre) {
        return new GenreOutput(
                genre.getId().getValue(),
                genre.getName(),
                genre.isActive(),
                genre.getCategories().stream()
                        .map(CategoryID::getValue)
                        .toList(),
                genre.getCreatedAt(),
                genre.getUpdatedAt(),
                genre.getDeletedAt()
        );
    }
}
