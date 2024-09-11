package tech.willeei.admin.catalogo.application.castmember.retrieve.get;

import tech.willeei.admin.catalogo.application.UseCase;

public abstract sealed class GetCastMemberByIdUseCase
        extends UseCase<String, CastMemberOutput>
        permits DefaultGetCastMemberByIdUseCase {
}
