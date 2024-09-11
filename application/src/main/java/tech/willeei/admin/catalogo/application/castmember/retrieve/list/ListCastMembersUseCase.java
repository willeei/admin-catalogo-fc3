package tech.willeei.admin.catalogo.application.castmember.retrieve.list;

import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;

public abstract sealed class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {

}
