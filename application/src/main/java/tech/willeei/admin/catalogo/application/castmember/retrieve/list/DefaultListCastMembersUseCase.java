package tech.willeei.admin.catalogo.application.castmember.retrieve.list;

import tech.willeei.admin.catalogo.domain.castmember.CastMemberGateway;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.pagination.SearchQuery;

import java.util.Objects;

public non-sealed class DefaultListCastMembersUseCase extends ListCastMembersUseCase {

    private final CastMemberGateway castMemberGateway;

    public DefaultListCastMembersUseCase(CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Override
    public Pagination<CastMemberListOutput> execute(final SearchQuery aQuery) {
        return this.castMemberGateway.findAll(aQuery)
                .map(CastMemberListOutput::from);
    }
}
