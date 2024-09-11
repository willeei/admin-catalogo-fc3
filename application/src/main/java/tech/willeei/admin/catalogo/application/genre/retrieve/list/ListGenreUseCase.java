package tech.willeei.admin.catalogo.application.genre.retrieve.list;

import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {

}
