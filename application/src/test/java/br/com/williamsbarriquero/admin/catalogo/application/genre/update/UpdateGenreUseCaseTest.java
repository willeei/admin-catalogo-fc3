package br.com.williamsbarriquero.admin.catalogo.application.genre.update;

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

import br.com.williamsbarriquero.admin.catalogo.application.UseCaseTest;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotificationException;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;

class UpdateGenreUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultUpdateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(categoryGateway, genreGateway);
    }

    @Test
    void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("atc", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));

        verify(genreGateway, times(1)).update(argThat(aUpdateGenre
                -> Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.isNull(aUpdateGenre.getDeletedAt())
        ));
    }

    @Test
    void givenAValidCommandWithCategories_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("atc", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(
                CategoryID.from("123"),
                CategoryID.from("456")
        );

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(expectedCategories);

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());
        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));

        verify(genreGateway, times(1)).update(argThat(aUpdateGenre
                -> Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.isNull(aUpdateGenre.getDeletedAt())
        ));
    }

    @Test
    void givenAValidCommandWithInactiveGenre_whenCallsUpdateGenre_shouldReturnGenreId() {
        // given
        final var aGenre = Genre.newGenre("atc", true);

        final var expectedId = aGenre.getId();
        final var expectedName = "Action";
        final var expectedIsActive = false;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(genreGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aGenre.isActive());
        Assertions.assertNull(aGenre.getDeletedAt());
        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertEquals(expectedId.getValue(), actualOutput.id());

        verify(genreGateway, times(1)).findById(eq(expectedId));

        verify(genreGateway, times(1)).update(argThat(aUpdateGenre
                -> Objects.equals(expectedId, aUpdateGenre.getId())
                && Objects.equals(expectedName, aUpdateGenre.getName())
                && Objects.equals(expectedIsActive, aUpdateGenre.isActive())
                && Objects.equals(expectedCategories, aUpdateGenre.getCategories())
                && Objects.equals(aGenre.getCreatedAt(), aUpdateGenre.getCreatedAt())
                && aGenre.getUpdatedAt().isBefore(aUpdateGenre.getUpdatedAt())
                && Objects.nonNull(aUpdateGenre.getDeletedAt())
        ));
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateGenre_shouldReturnNotificationException() {
        // given
        final var aGenre = Genre.newGenre("atc", true);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        // when
        final var actualException
                = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(0)).existsByIds(any());
        verify(genreGateway, times(0)).update(any());
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateGenreAndSomeCategoriesDoesNotExists_shouldReturnNotificationException() {
        // given
        final var filmes = CategoryID.from("123");
        final var series = CategoryID.from("456");
        final var documentarios = CategoryID.from("789");

        final var aGenre = Genre.newGenre("atc", true);

        final var expectedId = aGenre.getId();
        final String expectedName = null;
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes, series, documentarios);

        final var expectedErrorCount = 2;
        final var expectedErrorMessageOne = "Some categories could not be found: 456, 789";
        final var expectedErrorMessageTwo = "'name' should not be null";

        final var aCommand = UpdateGenreCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedIsActive,
                asString(expectedCategories)
        );

        when(genreGateway.findById(any()))
                .thenReturn(Optional.of(Genre.with(aGenre)));

        when(categoryGateway.existsByIds(any()))
                .thenReturn(List.of(filmes));

        // when
        final var actualException
                = Assertions.assertThrows(NotificationException.class, () -> useCase.execute(aCommand));

        // then
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessageOne, actualException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorMessageTwo, actualException.getErrors().get(1).message());

        verify(genreGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).existsByIds(eq(expectedCategories));
        verify(genreGateway, times(0)).update(any());
    }
}
