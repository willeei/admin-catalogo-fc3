package br.com.williamsbarriquero.admin.catalogo.application;

import br.com.williamsbarriquero.admin.catalogo.domain.Category;

public class UseCase {

    public Category execute() {
        return new Category();
    }

}