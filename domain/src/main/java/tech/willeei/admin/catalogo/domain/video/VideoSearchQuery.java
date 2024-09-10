package tech.willeei.admin.catalogo.domain.video;

import java.util.Set;

import tech.willeei.admin.catalogo.domain.castmember.CastMemberID;
import tech.willeei.admin.catalogo.domain.category.CategoryID;
import tech.willeei.admin.catalogo.domain.genre.GenreID;

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
