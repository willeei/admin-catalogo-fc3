package br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;

public abstract sealed class ListCastMembersUseCase
        extends UseCase<SearchQuery, Pagination<CastMemberListOutput>>
        permits DefaultListCastMembersUseCase {

}
