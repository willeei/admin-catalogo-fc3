package tech.willeei.admin.catalogo.infrastructure.configuration.usecases;

import tech.willeei.admin.catalogo.application.castmember.create.CreateCastMemberUseCase;
import tech.willeei.admin.catalogo.application.castmember.create.DefaultCreateCastMemberUseCase;
import tech.willeei.admin.catalogo.application.castmember.delete.DefaultDeleteCastMemberUseCase;
import tech.willeei.admin.catalogo.application.castmember.delete.DeleteCastMemberUseCase;
import tech.willeei.admin.catalogo.application.castmember.retrieve.get.DefaultGetCastMemberByIdUseCase;
import tech.willeei.admin.catalogo.application.castmember.retrieve.get.GetCastMemberByIdUseCase;
import tech.willeei.admin.catalogo.application.castmember.retrieve.list.DefaultListCastMembersUseCase;
import tech.willeei.admin.catalogo.application.castmember.retrieve.list.ListCastMembersUseCase;
import tech.willeei.admin.catalogo.application.castmember.update.DefaultUpdateCastMemberUseCase;
import tech.willeei.admin.catalogo.application.castmember.update.UpdateCastMemberUseCase;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class CastMemberUseCaseConfig {

    private final CastMemberGateway castMemberGateway;

    public CastMemberUseCaseConfig(final CastMemberGateway castMemberGateway) {
        this.castMemberGateway = Objects.requireNonNull(castMemberGateway);
    }

    @Bean
    public CreateCastMemberUseCase createCastMemberUseCase() {
        return new DefaultCreateCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public DeleteCastMemberUseCase deleteCastMemberUseCase() {
        return new DefaultDeleteCastMemberUseCase(castMemberGateway);
    }

    @Bean
    public GetCastMemberByIdUseCase getCastMemberByIdUseCase() {
        return new DefaultGetCastMemberByIdUseCase(castMemberGateway);
    }

    @Bean
    public ListCastMembersUseCase listCastMembersUseCase() {
        return new DefaultListCastMembersUseCase(castMemberGateway);
    }

    @Bean
    public UpdateCastMemberUseCase updateCastMemberUseCase() {
        return new DefaultUpdateCastMemberUseCase(castMemberGateway);
    }
}
