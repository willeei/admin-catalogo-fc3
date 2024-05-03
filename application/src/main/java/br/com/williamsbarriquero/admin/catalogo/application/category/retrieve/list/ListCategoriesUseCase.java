package br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {

}
