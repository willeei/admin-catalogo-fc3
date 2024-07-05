package br.com.williamsbarriquero.admin.catalogo.application.genre.delete;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.williamsbarriquero.admin.catalogo.IntegrationTest;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.persistence.GenreRepository;

@IntegrationTest
class DeleteGenreUseCaseIT {

    @Autowired
    private DeleteGenreUseCase useCase;

    @Autowired
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        //given
        final var aGenre = genreGateway.create(Genre.newGenre("Acao", true));
        final var expectedId = aGenre.getId();

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        // then
        Assertions.assertEquals(0, genreRepository.count());
    }

    @Test
    void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        //given
        genreGateway.create(Genre.newGenre("Acao", true));

        final var expectedId = GenreID.from("123");

        Assertions.assertEquals(1, genreRepository.count());

        // when
        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        Assertions.assertEquals(1, genreRepository.count());
    }

}
