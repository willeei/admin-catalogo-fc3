package br.com.williamsbarriquero.admin.catalogo.application.genre.create;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import br.com.williamsbarriquero.admin.catalogo.IntegrationTest;
import br.com.williamsbarriquero.admin.catalogo.domain.category.Category;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreGateway;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.persistence.GenreRepository;

@IntegrationTest
class CreateGenreUseCaseIT {

    @Autowired
    private CreateGenreUseCase useCase;

    @SpyBean
    private CategoryGateway categoryGateway;

    @SpyBean
    private GenreGateway genreGateway;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        // given
        final var filmes
                = categoryGateway.create(Category.newCategory("Filmes", null, true));
        final var expectedName = "Action";
        final var expectedIsActive = true;
        final var expectedCategories = List.of(filmes.getId());

        final var aCommand = new CreateGenreCommand(expectedName, expectedIsActive, asString(expectedCategories));

        // when
        final var actualOutput = useCase.execute(aCommand);

        // then
        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var genre = genreRepository.findById(actualOutput.id()).orElseThrow();

        Assertions.assertEquals(expectedName, genre.getName());
        Assertions.assertEquals(expectedIsActive, genre.isActive());
        Assertions.assertTrue(
                expectedCategories.size() == genre.getCategoryIDs().size()
                && expectedCategories.containsAll(genre.getCategoryIDs())
        );
        Assertions.assertNotNull(genre.getId());
        Assertions.assertNotNull(genre.getCreatedAt());
        Assertions.assertNotNull(genre.getUpdatedAt());
        Assertions.assertNull(genre.getDeletedAt());

    }

    private List<String> asString(List<CategoryID> categoryIDs) {
        return categoryIDs.stream().map(CategoryID::getValue).toList();
    }
}
