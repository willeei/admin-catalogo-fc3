package br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategorySearchQuery;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {

}
