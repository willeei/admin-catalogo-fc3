package br.com.williamsbarriquero.admin.catalogo.application.castmember.delete;

import br.com.williamsbarriquero.admin.catalogo.application.UnitUseCase;

public abstract sealed class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase {

}
