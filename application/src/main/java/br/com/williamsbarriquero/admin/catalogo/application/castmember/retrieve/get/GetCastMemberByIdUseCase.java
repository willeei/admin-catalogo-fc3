package br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.get;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;

public abstract sealed class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {

}
