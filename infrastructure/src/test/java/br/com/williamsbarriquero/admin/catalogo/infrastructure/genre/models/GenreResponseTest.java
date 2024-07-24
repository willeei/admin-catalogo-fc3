package br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models;

import br.com.williamsbarriquero.admin.catalogo.JacksonTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;
import java.util.List;

@JacksonTest
class GenreResponseTest {

    @Autowired
    private JacksonTester<GenreResponse> jacksonTester;

    @Test
    void testMarchall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var response = new GenreResponse(
                expectedId,
                expectedName,
                expectedCategories,
                expectedIsActive,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt
        );

        final var actualJson = this.jacksonTester.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.categories_id", expectedCategories)
                .hasJsonPathValue("$.is_active", expectedIsActive)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());
    }

    @Test
    void testUnmarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = "Ação";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var json = """
                {
                  "id": "%s",
                  "name": "%s",
                  "categories_id": ["%s", "%s"],
                  "is_active": %s,
                  "created_at": "%s",
                  "deleted_at": "%s",
                  "updated_at": "%s"
                }
                """.formatted(
                expectedId,
                expectedName,
                expectedCategories.get(0),
                expectedCategories.get(1),
                expectedIsActive,
                expectedCreatedAt.toString(),
                expectedDeletedAt.toString(),
                expectedUpdatedAt.toString()
        );

        final var actualJson = this.jacksonTester.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("categories", expectedCategories)
                .hasFieldOrPropertyWithValue("active", expectedIsActive)
                .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
                .hasFieldOrPropertyWithValue("deletedAt", expectedDeletedAt)
                .hasFieldOrPropertyWithValue("updatedAt", expectedUpdatedAt);
    }

}

