package tech.willeei.admin.catalogo.application.castmember.create;

import tech.willeei.admin.catalogo.application.UseCase;

public abstract sealed class CreateCastMemberUseCase
        extends UseCase<CreateCastMemberCommand, CreateCastMemberOutput>
        permits DefaultCreateCastMemberUseCase {

}
