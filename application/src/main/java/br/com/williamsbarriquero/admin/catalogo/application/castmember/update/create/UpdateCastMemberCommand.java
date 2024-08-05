package br.com.williamsbarriquero.admin.catalogo.application.castmember.update.create;

import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberType;

public record UpdateCastMemberCommand(
        String id,
        String name,
        CastMemberType type
) {

    public static UpdateCastMemberCommand with(final String anId, final String aName, final CastMemberType aType) {
        return new UpdateCastMemberCommand(anId, aName, aType);
    }
}
