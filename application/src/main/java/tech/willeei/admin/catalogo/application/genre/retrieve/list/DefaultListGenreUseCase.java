package tech.willeei.admin.catalogo.application.genre.retrieve.list;

import tech.willeei.admin.catalogo.domain.genre.GenreGateway;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListGenreUseCase extends ListGenreUseCase {

    private final GenreGateway genreGateway;

    public DefaultListGenreUseCase(final GenreGateway genreGateway) {
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Override
    public Pagination<GenreListOutput> execute(final SearchQuery aQuery) {
        return this.genreGateway.findAll(aQuery)
                .map(GenreListOutput::from);
    }
}
