package tech.willeei.admin.catalogo.application.castmember.retrieve.get;

import java.time.Instant;

import tech.willeei.admin.catalogo.domain.castmember.CastMember;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberType;

public record CastMemberOutput(
        String id,
        String name,
        CastMemberType type,
        Instant createdAt,
        Instant updatedAt) {

    public static CastMemberOutput from(final CastMember aMember) {
        return new CastMemberOutput(
                aMember.getId().getValue(),
                aMember.getName(),
                aMember.getType(),
                aMember.getCreatedAt(),
                aMember.getUpdatedAt()
        );
    }
}
