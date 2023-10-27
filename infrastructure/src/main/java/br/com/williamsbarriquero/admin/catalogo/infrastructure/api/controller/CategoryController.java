package br.com.williamsbarriquero.admin.catalogo.infrastructure.api.controller;

import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.api.CategoryAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController implements CategoryAPI {

    @Override
    public ResponseEntity<?> createCategory() {
        return null;
    }

    @Override
    public Pagination<?> listCategories(String search, int page, int perPage, String sort, String directio) {
        return null;
    }
}
