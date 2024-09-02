package br.com.williamsbarriquero.admin.catalogo.domain.castmember;

import java.util.List;
import java.util.Optional;

import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.SearchQuery;

public interface CastMemberGateway {

    CastMember create(CastMember aCastMember);

    void deleteById(CastMemberID anId);

    List<CastMemberID> existsByIds(Iterable<CastMemberID> anIds);

    Pagination<CastMember> findAll(SearchQuery aQuery);

    Optional<CastMember> findById(CastMemberID anId);

    CastMember update(CastMember aCastMember);
}
