package br.com.williamsbarriquero.admin.catalogo.infrastructure.castmember.models;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import br.com.williamsbarriquero.admin.catalogo.JacksonTest;
import br.com.williamsbarriquero.admin.catalogo.domain.Fixture;

@JacksonTest
class CastMemberResponseTest {

    @Autowired
    private JacksonTester<CastMemberResponse> json;

    @Test
    void testMarshall() throws Exception {
        final var expectedId = "123";
        final var expectedName = Fixture.name();
        final var expectedType = Fixture.CastMembers.type().name();
        final var expectedCreatedAt = Instant.now().toString();
        final var expectedUpdatedAt = Instant.now().toString();

        final var response = new CastMemberResponse(
                expectedId,
                expectedName,
                expectedType,
                expectedCreatedAt,
                expectedUpdatedAt
        );

        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.type", expectedType)
                .hasJsonPathValue("$.created_at", expectedCreatedAt)
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt);
    }
}
