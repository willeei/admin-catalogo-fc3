package br.com.williamsbarriquero.admin.catalogo.application.castmember.delete;

import br.com.williamsbarriquero.admin.catalogo.domain.Fixture;
import br.com.williamsbarriquero.admin.catalogo.IntegrationTest;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMember;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.castmember.CastMemberID;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.persistence.CastMemberJpaEntity;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.persistence.CastMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@IntegrationTest
class DeleteCastMembersUseCaseIT {

    @Autowired
    private DeleteCastMemberUseCase useCase;

    @Autowired
    private CastMemberRepository castMemberRepository;

    @SpyBean
    private CastMemberGateway castMemberGateway;

    @Test
    void givenAValidId_whenCallsDeleteCastMember_shouldDeleteIt() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());
        final var aMemberTwo = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = aMember.getId();

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));
        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMemberTwo));

        Assertions.assertEquals(2, castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, castMemberRepository.count());
        Assertions.assertFalse(castMemberRepository.existsById(expectedId.getValue()));
        Assertions.assertTrue(castMemberRepository.existsById(aMemberTwo.getId().getValue()));
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteCastMember_shouldBeOk() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = CastMemberID.from("123");

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, castMemberRepository.count());
    }

    @Test
    void givenAValidId_whenCallsDeleteCastMemberAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var aMember = CastMember.newMember(Fixture.name(), Fixture.CastMembers.type());

        final var expectedId = CastMemberID.from("123");

        this.castMemberRepository.saveAndFlush(CastMemberJpaEntity.from(aMember));

        Assertions.assertEquals(1, castMemberRepository.count());

        doThrow(new IllegalStateException("Gateway error")).when(castMemberGateway).deleteById(any());

        // when
        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        // then
        verify(castMemberGateway).deleteById(eq(expectedId));

        Assertions.assertEquals(1, castMemberRepository.count());
    }
}
