package tech.willeei.admin.catalogo.application.castmember.update;

import tech.willeei.admin.catalogo.domain.castmember.CastMember;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberID;

public record UpdateCastMemberOutput(
        String id) {

    public static UpdateCastMemberOutput from(final CastMemberID anId) {
        return new UpdateCastMemberOutput(anId.getValue());
    }

    public static UpdateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}
