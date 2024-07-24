package br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.presenters;

import br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.get.CategoryOutput;
import br.com.williamsbarriquero.admin.catalogo.application.category.retrieve.list.CategoryListOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get.GenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.list.GenreListOutput;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.models.CategoryListResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.category.models.CategoryResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models.GenreListResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models.GenreResponse;

public interface GenreApiPresenter {

    static GenreResponse present(final GenreOutput output) {
        return new GenreResponse(
                output.id(),
                output.name(),
                output.categories(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }

    static GenreListResponse present(final GenreListOutput output) {
        return new GenreListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt()
        );
    }
}
