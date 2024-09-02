package br.com.williamsbarriquero.admin.catalogo.domain.genre;

import java.util.List;
import java.util.Optional;

import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;

public interface GenreGateway {

    Genre create(Genre aGenre);

    void deleteById(GenreID anId);

    List<Genre> existsByIds(List<GenreID> anIds);

    Optional<Genre> findById(GenreID anId);

    Genre update(Genre aGenre);

    Pagination<Genre> findAll(SearchQuery aSearchQuery);
}
