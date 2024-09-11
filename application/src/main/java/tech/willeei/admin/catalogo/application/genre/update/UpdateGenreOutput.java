package tech.willeei.admin.catalogo.application.genre.update;

import tech.willeei.admin.catalogo.domain.genre.Genre;

public record UpdateGenreOutput(String id) {

    public static UpdateGenreOutput from(final Genre aGenre) {
        return new UpdateGenreOutput(aGenre.getId().getValue());
    }
}
