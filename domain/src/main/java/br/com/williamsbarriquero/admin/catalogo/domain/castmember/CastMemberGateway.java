package br.com.williamsbarriquero.admin.catalogo.domain.castmember;

import java.util.List;

import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Optional;

public interface CastMemberGateway {

    CastMember create(CastMember aCastMember);

    void deleteById(CastMemberID anId);

    List<CastMember> existsByIds(List<CastMemberID> anIds);

    Pagination<CastMember> findAll(SearchQuery aQuery);

    Optional<CastMember> findById(CastMemberID anId);

    CastMember update(CastMember aCastMember);
}
