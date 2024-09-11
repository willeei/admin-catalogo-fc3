package tech.willeei.admin.catalogo.infrastructure.category.presenters;

import tech.willeei.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import tech.willeei.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import tech.willeei.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import tech.willeei.admin.catalogo.infrastructure.category.models.CategoryResponse;

public interface CategoryApiPresenter {

    static CategoryResponse present(final CategoryOutput output) {
        return new CategoryResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static CategoryListResponse present(final CategoryListOutput output) {
        return new CategoryListResponse(
                output.id().getValue(),
                output.name(),
                output.description(),
                output.isActive(),
                output.createdAt(),
                output.deletedAt()
        );
    }
}
