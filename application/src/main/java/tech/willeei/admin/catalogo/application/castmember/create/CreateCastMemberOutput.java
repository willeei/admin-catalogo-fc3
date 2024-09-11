package tech.willeei.admin.catalogo.application.castmember.create;

import tech.willeei.admin.catalogo.domain.castmember.CastMember;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberID;

public record CreateCastMemberOutput(
        String id) {

    public static CreateCastMemberOutput from(CastMemberID anId) {
        return new CreateCastMemberOutput(anId.getValue());
    }

    public static CreateCastMemberOutput from(final CastMember aMember) {
        return from(aMember.getId());
    }
}
