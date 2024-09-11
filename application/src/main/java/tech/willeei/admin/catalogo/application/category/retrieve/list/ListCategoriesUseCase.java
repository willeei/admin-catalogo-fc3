package tech.willeei.admin.catalogo.application.category.retrieve.list;

import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<SearchQuery, Pagination<CategoryListOutput>> {

}
