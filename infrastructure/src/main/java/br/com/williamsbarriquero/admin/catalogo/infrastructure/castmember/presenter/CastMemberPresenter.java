package br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.presenter;

import br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.get.CastMemberOutput;
import br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.list.CastMemberListOutput;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.CastMemberListResponse;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models.CastMemberResponse;

public interface CastMemberPresenter {

    static CastMemberResponse present(final CastMemberOutput aMember) {
        return new CastMemberResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString(),
                aMember.updatedAt().toString()
        );
    }

    static CastMemberListResponse present(final CastMemberListOutput aMember) {
        return new CastMemberListResponse(
                aMember.id(),
                aMember.name(),
                aMember.type().name(),
                aMember.createdAt().toString()
        );
    }
}
