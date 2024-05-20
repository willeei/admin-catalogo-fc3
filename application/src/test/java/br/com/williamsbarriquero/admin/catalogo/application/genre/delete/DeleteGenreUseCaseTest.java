package br.com.williamsbarriquero.admin.catalogo.application.genre.delete;

import br.com.williamsbarriquero.admin.catalogo.application.UseCaseTest;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteGenreUseCase useCase;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(genreGateway);
    }

    @Test
    void givenAValidGenreId_whenCallsDeleteGenre_shouldDeleteGenre() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        doNothing()
                .when(genreGateway).deleteById(any());

        // when
        assertDoesNotThrow(() -> useCase.execute(expectedId));

        // then
        verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    void givenAnInvalidGenreId_whenCallsDeleteGenre_shouldBeOk() {
        //given
        final var expectedId = GenreID.from("123");

        doNothing()
                .when(genreGateway).deleteById(any());

        // when
        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        //then
        verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    void givenAValidGenreId_whenCallsDeleteGenreAndGatewayThrowsUnexpectedError_shoulReceiveException() {
        //given
        final var aGenre = Genre.newGenre("Acao", true);
        final var expectedId = aGenre.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(genreGateway).deleteById(any());

        //when
        assertThrows(IllegalStateException.class, () -> {
            useCase.execute(expectedId.getValue());
        });

        //then
        verify(genreGateway, times(1)).deleteById(eq(expectedId));
    }
}
