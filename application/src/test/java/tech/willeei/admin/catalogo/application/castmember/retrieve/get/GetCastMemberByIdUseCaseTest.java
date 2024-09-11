package tech.willeei.admin.catalogo.application.castmember.retrieve.get;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import tech.willeei.admin.catalogo.application.UseCaseTest;
import tech.willeei.admin.catalogo.domain.Fixture;
import tech.willeei.admin.catalogo.domain.castmember.CastMember;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberGateway;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberID;
import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;

class GetCastMemberByIdUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetCastMemberByIdUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of();
    }

    @Test
    void givenAValidId_whenCallsGetCastMemberById_shouldReturnIt() {
        // given
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var aMember = CastMember.newMember(expectedName, expectedType);

        final var expectedId = aMember.getId();

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(aMember));

        // when
        final var actualOutput = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());
        Assertions.assertEquals(expectedName, actualOutput.name());
        Assertions.assertEquals(expectedType, actualOutput.type());
        Assertions.assertEquals(aMember.getCreatedAt(), actualOutput.createdAt());
        Assertions.assertEquals(aMember.getUpdatedAt(), actualOutput.updatedAt());

        verify(castMemberGateway).findById(expectedId);
    }

    @Test
    void givenAnInvalidId_whenCallsGetCastMemberAndDoesNotExists_shouldReturnNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");

        final var expecteErrorMessage = "CastMember with ID 123 was not found";

        when(castMemberGateway.findById(any())).thenReturn(Optional.empty());

        // when
        final var notFoundException = NotFoundException.class;

        final var actualException = Assertions.assertThrows(
                notFoundException, () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expecteErrorMessage, actualException.getMessage());

        verify(castMemberGateway).findById(expectedId);
    }
}
