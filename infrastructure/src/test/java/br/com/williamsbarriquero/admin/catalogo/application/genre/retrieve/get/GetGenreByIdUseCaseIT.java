package br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.williamsbarriquero.admin.catalogo.IntegrationTest;
import br.com.williamsbarriquero.admin.catalogo.domain.category.Category;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;

@IntegrationTest
class GetGenreByIdUseCaseIT {

    @Autowired
    private GetGenreByIdUseCase useCase;

    @Autowired
    private CategoryGateway categoryGateway;

    @Autowired
    private GenreGateway genreGateway;

    @Test
    void givenAValidId_whenCallsGetGenre_shouldReturnGenre() {
        // given
        final var series
                = categoryGateway.create(Category.newCategory("Series", null, true));

        final var filmes
                = categoryGateway.create(Category.newCategory("Series", null, true));

        final var expectedName = "Acao";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(series.getId(), filmes.getId());

        final var aGenre = genreGateway.create(
                Genre.newGenre(expectedName, expectedIsActive)
                        .addCategories(expectedCategories)
        );

        final var expectedId = aGenre.getId();

        // when
        final var actualGenre = useCase.execute(expectedId.getValue());

        // then
        Assertions.assertEquals(expectedId.getValue(), actualGenre.id());
        Assertions.assertEquals(expectedName, actualGenre.name());
        Assertions.assertEquals(expectedIsActive, actualGenre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == actualGenre.categories().size()
                && asString(expectedCategories).containsAll(actualGenre.categories())
        );
        Assertions.assertEquals(aGenre.getCreatedAt(), actualGenre.createdAt());
        Assertions.assertEquals(aGenre.getUpdatedAt(), actualGenre.updatedAt());
        Assertions.assertEquals(aGenre.getDeletedAt(), actualGenre.deletedAt());
    }

    @Test
    void givenAValidId_whenCallsGetGenreAndDoesNotExists_thenShouldReturnNotFound() {
        // given
        final var expectedErrorMessage = "Genre with ID 123 was not found";
        final var expectedId = GenreID.from("123");

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        // then
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    private List<String> asString(List<CategoryID> ids) {
        return ids.stream()
                .map(CategoryID::getValue)
                .toList();
    }
}
