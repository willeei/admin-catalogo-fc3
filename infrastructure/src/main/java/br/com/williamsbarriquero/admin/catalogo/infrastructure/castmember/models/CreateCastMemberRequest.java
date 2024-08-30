package br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models;

import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberType;

public record CreateCastMemberRequest(String name, CastMemberType type) {

}
