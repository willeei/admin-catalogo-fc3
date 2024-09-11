package tech.willeei.admin.catalogo.application.category.retrieve.list;

import java.util.Objects;

import tech.willeei.admin.catalogo.domain.category.CategoryGateway;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;

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
