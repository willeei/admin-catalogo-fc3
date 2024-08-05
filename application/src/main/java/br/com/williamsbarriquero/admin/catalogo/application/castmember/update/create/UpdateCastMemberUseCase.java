package br.com.williamsbarriquero.admin.catalogo.application.castmember.update.create;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;

public abstract sealed class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {

}
