package tech.willeei.admin.catalogo.infrastructure.castmember.models;

import tech.willeei.admin.catalogo.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {

}
