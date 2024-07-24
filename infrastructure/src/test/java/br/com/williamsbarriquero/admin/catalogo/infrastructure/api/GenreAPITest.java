package br.com.williamsbarriquero.admin.catalogo.infrastructure.api;

import br.com.williamsbarriquero.admin.catalogo.ControllerTest;
import br.com.williamsbarriquero.admin.catalogo.application.genre.create.CreateGenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.create.CreateGenreUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get.GenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotificationException;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.Error;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.handler.Notification;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = GenreAPI.class)
class GenreAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateGenreUseCase createGenreUseCase;

    @MockBean
    private GetGenreByIdUseCase getGenreByIdUseCase;

    @Test
    void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() throws Exception {
        // given
        final var expectedName = "Action";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;
        final var expectedId = "123";

        final var aCommand =
                new CreateGenreRequest(expectedName, expectedCategories, expectedIsActive);

        when(createGenreUseCase.execute(any())).thenReturn(CreateGenreOutput.from(expectedId));

        // when
        final var aRequest = post("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest).andDo(print());

        // then
        aResponse
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/genres/" + expectedId))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(createGenreUseCase).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    void givenAnInvalidName_whenCallsCreateGenre_shouldReturnNotification() throws Exception {
        // given
        final var expectedCategories = List.of("Action", "456");
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aCommand =
                new CreateGenreRequest(null, expectedCategories, expectedIsActive);

        when(createGenreUseCase.execute(any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        // when
        final var aRequest = post("/genres")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest).andDo(print());

        // then
        aResponse
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(createGenreUseCase).execute(argThat(cmd ->
                Objects.equals(null, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    void givenAValidId_whenCallsGetGenreById_shouldReturnGenre() throws Exception {
        // given
        final var expectedName = "Action";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = false;

        final var aGenre = Genre.newGenre(expectedName, expectedIsActive)
                .addCategories(expectedCategories.stream().map(CategoryID::from).toList());

        final var expectedId = aGenre.getId().getValue();

        when(getGenreByIdUseCase.execute(any())).thenReturn(GenreOutput.from(aGenre));

        // when
        final var aRequest = get("/genres/" + expectedId)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var aResponse = this.mvc.perform(aRequest);

        // then
        aResponse
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.categories_id", equalTo(expectedCategories)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedIsActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aGenre.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aGenre.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", equalTo(aGenre.getDeletedAt().toString())));

        verify(getGenreByIdUseCase).execute(eq(expectedId));
    }

    @Test
    void givenAnInvalidId_whenCallsGetGenreById_shouldReturnNotFound() throws Exception {
        // given
        final var expectedErrorMessage = "Genre with ID 123 was not found";
        final var expectedId = GenreID.from("123");

        when(getGenreByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Genre.class, expectedId));

        // when
        final var aRequest = get("/genres/" + expectedId.getValue())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aRequest);

        // then
        response.andExpect(status().isNotFound())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));

        verify(getGenreByIdUseCase).execute(eq(expectedId.getValue()));
    }
}
