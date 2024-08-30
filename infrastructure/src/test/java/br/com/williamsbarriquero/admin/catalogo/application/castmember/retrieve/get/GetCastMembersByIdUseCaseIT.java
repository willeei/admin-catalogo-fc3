package br.com.williamsbarriquero.admin.catalogo.application.castmember.retrieve.get;

import br.com.williamsbarriquero.admin.catalogo.Fixture;
import br.com.williamsbarriquero.admin.catalogo.IntegrationTest;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMember;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@IntegrationTest
class GetCastMembersByIdUseCaseIT {

    @Autowired
    private GetCastMemberByIdUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    void givenAValidId_whenCallsGetCastMemberById_shouldReturnIt() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedType, actualOutput.type());
        Assertions.assertEquals(aMember.getCreatedAt(), actualOutput.createdAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualOutput.updatedAt());

        verify(castMemberGateway).findById(any());
    }

    @Test
    void givenAnInvalidId_whenCallsGetCastMemberAndDoesNotExists_shouldReturnNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");

        final var expecteErrorMessage = "CastMember with ID 123 was not found";

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expecteErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findById(eq(expectedId));
    }
}
