package tech.willeei.admin.catalogo.application.castmember.update;

import tech.willeei.admin.catalogo.application.UseCase;

public abstract sealed class UpdateCastMemberUseCase
        extends UseCase<UpdateCastMemberCommand, UpdateCastMemberOutput>
        permits DefaultUpdateCastMemberUseCase {

}
