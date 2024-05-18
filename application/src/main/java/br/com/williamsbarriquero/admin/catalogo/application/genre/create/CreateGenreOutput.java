package br.com.williamsbarriquero.admin.catalogo.application.category.create;

import br.com.williamsbarriquero.admin.catalogo.domain.category.Category;

public record CreateCategoryOutput(
        String id
) {

    public static CreateCategoryOutput from(final String anId) {
        return new CreateCategoryOutput(anId);
    }

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId().getValue());
    }
}
