package br.com.williamsbarriquero.admin.catalogo.application.castmember.delete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import br.com.williamsbarriquero.admin.catalogo.application.UseCaseTest;
import br.com.williamsbarriquero.admin.catalogo.domain.Fixture;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMember;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberID;

class DeleteCastMemberUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteCastMemberUseCase useCase;

    @Mock
    private CastMemberGateway castMemberGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(castMemberGateway);
    }

    @Test
    void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        doNothing().when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(expectedId);
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        final var expectedId = CastMemberID.from("123");

        doNothing().when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(expectedId);
    }

    @Test
    void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var expectedId = CastMemberID.from("123");

        doThrow(new IllegalStateException("Gateway error")).when(castMemberGateway).deleteById(any());

        // when
        final var expectedType = IllegalStateException.class;
        final var actualException = Assertions.assertThrows(expectedType, () -> useCase.execute(expectedId.getValue()));

        Assertions.assertNotNull(actualException);

        // then
        verify(castMemberGateway).deleteById(expectedId);
    }
}
