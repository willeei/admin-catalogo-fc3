package br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final SearchQuery anId) {
        return this.categoryGateway.findAll(anId)
                .map(CategoryListOutput::from);
    }
}
