package tech.willeei.admin.catalogo.infrastructure.genre.models;

import tech.willeei.admin.catalogo.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.util.List;

@JacksonTest
class CreateGenreRequestTest {

    @Autowired
    private JacksonTester<CreateGenreRequest> jacksonTester;

    @Test
    void testMarchall() throws Exception {
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;

        final var response = new CreateGenreRequest(
                expectedName,
                expectedCategories,
                expectedIsActive
        );

        final var actualJson = this.jacksonTester.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.categories_id", expectedCategories)
                .hasJsonPathValue("$.is_active", expectedIsActive);
    }

    @Test
    void testUnmarshall() throws Exception {
        final var expectedName = "Filmes";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;

        final var json = """
                {
                  "name": "%s",
                  "categories_id": ["%s", "%s"],
                  "is_active": %s
                }
                """.formatted(
                expectedName,
                expectedCategories.get(0),
                expectedCategories.get(1),
                expectedIsActive
        );

        final var actualJson = this.jacksonTester.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("categories", expectedCategories)
                .hasFieldOrPropertyWithValue("active", expectedIsActive);
    }

}
