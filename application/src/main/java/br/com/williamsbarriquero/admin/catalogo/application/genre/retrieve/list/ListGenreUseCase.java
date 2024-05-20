package br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;

public abstract class ListGenreUseCase extends UseCase<SearchQuery, Pagination<GenreListOutput>> {

}
