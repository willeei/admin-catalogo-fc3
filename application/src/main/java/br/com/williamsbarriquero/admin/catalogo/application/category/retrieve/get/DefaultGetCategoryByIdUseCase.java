package br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.get;

import br.com.williamsbarriquero.admin.catalogo.domain.category.Category;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotFoundException;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    private static Supplier<NotFoundException> notFound(CategoryID anId) {
        return () -> NotFoundException.with(Category.class, anId);
    }

    @Override
    public CategoryOutput execute(final String anIn) {
        final var aCategoryID = CategoryID.from(anIn);
        return this.categoryGateway.findById(aCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(aCategoryID));
    }
}
