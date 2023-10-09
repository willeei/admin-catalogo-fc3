package br.com.williamsbarriquero.admin.catalogo.application.category.delete;

import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Override
    public void execute(String anIn) {
        this.categoryGateway.deleteById(CategoryID.from(anIn));
    }
}
