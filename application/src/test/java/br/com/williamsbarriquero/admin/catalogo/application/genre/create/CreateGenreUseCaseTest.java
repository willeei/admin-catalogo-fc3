package br.com.williamsbarriquero.admin.catalogo.application.genre.create;

import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateGenreUseCaseTest {

    @InjectMocks
    private DefaultCreateGenreUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Mock
    private GenreGateway genreGateway;

    @Test
    void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        // given
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.<CategoryID>of();

        final var aCommand =
                new CreateGenreCommand(expectedName, expectedIsActive, asString(expectedCategories));

        when(genreGateway.create(any())).thenAnswer(returnsFirstArg());

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(genreGateway, times(1)).create(argThat(aGenre ->
                Objects.equals(expectedName, aGenre.getName()) &&
                        Objects.equals(expectedIsActive, aGenre.isActive()) &&
                        Objects.equals(expectedCategories, aGenre.getCategories()) &&
                        Objects.nonNull(aGenre.getId()) &&
                        Objects.nonNull(aGenre.getCreatedAt()) &&
                        Objects.nonNull(aGenre.getUpdatedAt()) &&
                        Objects.isNull(aGenre.getDeletedAt())

        ));

    }

    private List<String> asString(List<CategoryID> categoryIDs) {
        return categoryIDs.stream()
                .map(CategoryID::getValue)
                .toList();
    }
}
