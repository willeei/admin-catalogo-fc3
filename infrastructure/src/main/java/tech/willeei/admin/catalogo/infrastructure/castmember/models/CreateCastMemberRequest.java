package tech.willeei.admin.catalogo.infrastructure.castmember.models;

import tech.willeei.admin.catalogo.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {

}
