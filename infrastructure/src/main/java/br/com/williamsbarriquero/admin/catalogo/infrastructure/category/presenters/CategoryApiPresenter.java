package br.com.williamsbarriquero.admin.catalogo.infrastructure.category.presenters;

import br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.models.CategoryResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.models.CategoryListResponse;

public interface CategoryApiPresenter {

//    Function<CategoryOutput, CategoryApiOutput> present =
//            output -> new CategoryApiOutput(
//                    output.id().getValue(),
//                    output.name(),
//                    output.description(),
//                    output.isActive(),
//                    output.createdAt(),
//                    output.updatedAt(),
//                    output.deletedAt()
//            );

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
