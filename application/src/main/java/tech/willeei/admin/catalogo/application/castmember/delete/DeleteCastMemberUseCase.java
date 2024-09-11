package tech.willeei.admin.catalogo.application.castmember.delete;

import tech.willeei.admin.catalogo.application.UnitUseCase;

public abstract sealed class DeleteCastMemberUseCase
        extends UnitUseCase<String>
        permits DefaultDeleteCastMemberUseCase {

}
