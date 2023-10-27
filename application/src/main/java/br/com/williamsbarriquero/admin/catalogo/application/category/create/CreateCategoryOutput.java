package br.com.williamsbarriquero.admin.catalogo.application.category.create;

import br.com.williamsbarriquero.admin.catalogo.domain.category.Category;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id
) {

    public static CreateCategoryOutput from(final CategoryID anId) {
        return new CreateCategoryOutput(anId);
    }

    public static CreateCategoryOutput from(final Category category) {
        return new CreateCategoryOutput(category.getId());
    }
}
