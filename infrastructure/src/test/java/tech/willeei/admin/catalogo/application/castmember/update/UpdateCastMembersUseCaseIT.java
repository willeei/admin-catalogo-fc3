package tech.willeei.admin.catalogo.application.castmember.update;

import tech.willeei.admin.catalogo.domain.Fixture;
import tech.willeei.admin.catalogo.IntegrationTest;
import tech.willeei.admin.catalogo.domain.castmember.CastMember;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberGateway;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberID;
import tech.willeei.admin.catalogo.domain.castmember.CastMemberType;
import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;
import tech.willeei.admin.catalogo.domain.exceptions.NotificationException;
import tech.willeei.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import tech.willeei.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@IntegrationTest
class UpdateCastMembersUseCaseIT {

    @Autowired
    private UpdateCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    void givenAValidCommand_whenCallsUpdateCastMember_shouldReturnItsIdentifier() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final var expectedType = CastMemberType.ACTOR;

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        // when
        final var actualOutput = useCase.execute(aCommand);

        //then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        final var actualPersistedMember = this.castMemberRepository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(expectedName, actualPersistedMember.getName());
        Assertions.assertEquals(expectedType, actualPersistedMember.getType());
        Assertions.assertEquals(aMember.getCreatedAt(), actualPersistedMember.getCreatedAt());
        Assertions.assertTrue(aMember.getUpdatedAt().isBefore(actualPersistedMember.getUpdatedAt()));

        verify(castMemberGateway).findById(any());
        verify(castMemberGateway).update(any());
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final String expectedName = null;
        final var expectedType = CastMemberType.ACTOR;

        final var expectedErrorCount = 1;
        final var expectedErrorMessages = "'name' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessages, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(any());

        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    void givenAnInvalidType_whenCallsUpdateCastMember_shouldThrowsNotificationException() {
        // given
        final var aMember = CastMember.newMember("vin diesel", CastMemberType.DIRECTOR);

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        final var expectedId = aMember.getId();
        final var expectedName = Fixture.name();
        final CastMemberType expectedType = null;

        final var expectedErrorCount = 1;
        final var expectedErrorMessages = "'type' should not be null";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        // when
        final var actualException = Assertions.assertThrows(
                NotificationException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessages, actualException.getErrors().get(0).message());

        verify(castMemberGateway).findById(any());

        verify(castMemberGateway, times(0)).update(any());
    }

    @Test
    void givenAnInvalidId_whenCallsUpdateCastMember_shouldThrowsNotFoundException() {
        // given
        final var expectedId = CastMemberID.from("123");
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type();

        final var expectedErrorMessages = "CastMember with ID 123 was not found";

        final var aCommand = UpdateCastMemberCommand.with(expectedId.getValue(), expectedName, expectedType);

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> useCase.execute(aCommand)
        );

        //then
        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorMessages, actualException.getMessage());

        verify(castMemberGateway).findById(any());

        verify(castMemberGateway, times(0)).update(any());
    }
}
