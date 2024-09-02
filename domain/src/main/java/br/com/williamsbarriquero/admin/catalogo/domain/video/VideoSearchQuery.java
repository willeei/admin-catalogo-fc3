package br.com.williamsbarriquero.admin.catalogo.domain.video;

import java.util.Set;

import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberID;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;

public record VideoSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction,
        Set<CastMemberID> castMembers,
        Set<CategoryID> categories,
        Set<GenreID> genres) {

}
