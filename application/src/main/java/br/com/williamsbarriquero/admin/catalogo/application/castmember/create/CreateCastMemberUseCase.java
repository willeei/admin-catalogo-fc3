package br.com.williamsbarriquero.admin.catalogo.application.castmember.create;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;

public abstract sealed class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {

}
