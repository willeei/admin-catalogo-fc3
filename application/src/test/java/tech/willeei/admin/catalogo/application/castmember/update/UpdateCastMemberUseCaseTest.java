package tech.willeei.admin.catalogo.application.castmember.update;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Objects;
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
import tech.willeei.admin.catalogo.domain.castmember.CastMemberType;
import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;
import tech.willeei.admin.catalogo.domain.exceptions.NotificationException;

class UpdateCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnItsIdentifier() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(CastMember.with(aMember)));

        when(castMemberGateway.update(any())).thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        verify(castMemberGateway).findById(expectedId);

        verify(castMemberGateway).update(argThat(aUpdatedMember
                -> Objects.equals(expectedId, aUpdatedMember.getId())
                && Objects.equals(expectedName, aUpdatedMember.getName())
                && Objects.equals(expectedType, aUpdatedMember.getType())
                && Objects.equals(aMember.getCreatedAt(), aUpdatedMember.getCreatedAt())
                && aMember.getUpdatedAt().isBefore(aUpdatedMember.getUpdatedAt())
        ));
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        final var expectedId = aMember.getId();
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;

        final var expectedErrorCount = 1;
        final var expectedErrorMessages = "'name' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(aMember));

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessages, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(expectedId);

        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    void givenAnInvalidType_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessages = "'type' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        when(castMemberGateway.findById(any())).thenReturn(Optional.of(aMember));

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessages, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(expectedId);

        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    void givenAnInvalidId_whenCallsUpdateCastMember_shouldThrowsNotFoundException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        final var expectedId = CastMemberID.from("123");
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var expectedErrorMessages = "CastMember with ID 123 was not found";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        when(castMemberGateway.findById(any())).thenReturn(Optional.empty());

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessages, actualException.getMessage());

        verify(castMemberGateway).findById(eq(expectedId));

        verify(castMemberGateway, times(0)).update(any());
    }
}
