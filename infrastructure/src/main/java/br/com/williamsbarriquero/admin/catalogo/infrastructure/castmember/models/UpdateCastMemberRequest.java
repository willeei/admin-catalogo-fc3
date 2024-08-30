package br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models;

import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberType;

public record UpdateCastMemberRequest(String name, CastMemberType type) {

}
