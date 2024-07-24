package br.com.williamsbarriquero.admin.catalogo.infrastructure.api;

import br.com.williamsbarriquero.admin.catalogo.ControllerTest;
import br.com.williamsbarriquero.admin.catalogo.application.genre.create.CreateGenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.create.CreateGenreUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get.GenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.list.GenreListOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.retrieve.list.ListGenreUseCase;
import br.com.williamsbarriquero.admin.catalogo.application.genre.update.UpdateGenreOutput;
import br.com.williamsbarriquero.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.category.CategoryID;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotFoundException;
import br.com.williamsbarriquero.admin.catalogo.domain.exceptions.NotificationException;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.Genre;
import br.com.williamsbarriquero.admin.catalogo.domain.genre.GenreID;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.Error;
import br.com.williamsbarriquero.admin.catalogo.domain.validation.handler.Notification;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models.CreateGenreRequest;
import br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models.UpdateGenreRequest;
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
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @MockBean
    private UpdateGenreUseCase updateGenreUseCase;

    @MockBean
    private DeleteGenreUseCase deleteGenreUseCase;

    @MockBean
    private ListGenreUseCase listGenreUseCase;

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

        verify(getGenreByIdUseCase).execute(expectedId);
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

        verify(getGenreByIdUseCase).execute(expectedId.getValue());
    }

    @Test
    void givenAValidCommand_whenCallsUpdateGenre_shouldReturnGenreId() throws Exception {
        // given
        final var expectedName = "Action";
        final var expectedCategories = List.of("123", "456");
        final var expectedIsActive = true;

        final var aGenre = Genre.newGenre(expectedName, expectedIsActive)
                .addCategories(expectedCategories.stream().map(CategoryID::from).toList());
        final var expectedId = aGenre.getId().getValue();

        final var aCommand =
                new UpdateGenreRequest(expectedName, expectedCategories, expectedIsActive);

        when(updateGenreUseCase.execute(any())).thenReturn(UpdateGenreOutput.from(aGenre));

        // when
        final var aRequest = put("/genres/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest).andDo(print());

        // then
        aResponse
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)));

        verify(updateGenreUseCase).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    void givenAnInvalidName_whenCallsUpdateGenre_shouldReturnNotification() throws Exception {
        // given
        final var expectedCategories = List.of("Action", "456");
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";

        final var aGenre = Genre.newGenre("Action", expectedIsActive)
                .addCategories(expectedCategories.stream().map(CategoryID::from).toList());
        final var expectedId = aGenre.getId().getValue();

        final var aCommand =
                new UpdateGenreRequest(null, expectedCategories, expectedIsActive);

        when(updateGenreUseCase.execute(any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        // when
        final var aRequest = put("/genres/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aCommand));

        final var aResponse = this.mvc.perform(aRequest).andDo(print());

        // then
        aResponse
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", equalTo(expectedErrorMessage)));

        verify(updateGenreUseCase).execute(argThat(cmd ->
                Objects.equals(null, cmd.name())
                        && Objects.equals(expectedCategories, cmd.categories())
                        && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }

    @Test
    void givenAValidId_whenCallsDeleteGenre_shouldBeOk() throws Exception {
        // given
        final var expectedId = "123";

        doNothing().when(deleteGenreUseCase).execute(any());

        // when
        final var aRequest = delete("/genres/{id}", expectedId)
                .contentType(MediaType.APPLICATION_JSON);

        final var result = this.mvc.perform(aRequest);

        result.andExpect(status().isNoContent());

        verify(deleteGenreUseCase).execute(expectedId);
    }

    @Test
    void givenValidParams_whenCallsListGenres_shouldReturnGenres() throws Exception {
        // given
        final var aGenre = Genre.newGenre("Action", false);

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Ac";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var expectedItemsCount = 1;
        final var expectedTotal = 1;

        final var expectedItems = List.of(GenreListOutput.from(aGenre));

        when(listGenreUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

        // when
        final var aRequest = get("/genres")
                .queryParam("page", String.valueOf(expectedPage))
                .queryParam("perPage", String.valueOf(expectedPerPage))
                .queryParam("sort", expectedSort)
                .queryParam("dir", expectedDirection)
                .queryParam("search", expectedTerms)
                .accept(MediaType.APPLICATION_JSON);

        final var response = this.mvc.perform(aRequest);

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.current_page", equalTo(expectedPage)))
                .andExpect(jsonPath("$.per_page", equalTo(expectedPerPage)))
                .andExpect(jsonPath("$.total", equalTo(expectedTotal)))
                .andExpect(jsonPath("$.items", hasSize(expectedItemsCount)))
                .andExpect(jsonPath("$.items[0].id", equalTo(aGenre.getId().getValue())))
                .andExpect(jsonPath("$.items[0].name", equalTo(aGenre.getName())))
                .andExpect(jsonPath("$.items[0].is_active", equalTo(aGenre.isActive())))
                .andExpect(jsonPath("$.items[0].created_at", equalTo(aGenre.getCreatedAt().toString())))
                .andExpect(jsonPath("$.items[0].updated_at", equalTo(aGenre.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.items[0].deleted_at", equalTo(aGenre.getDeletedAt().toString())));

        verify(listGenreUseCase).execute(argThat(query ->
                Objects.equals(expectedPage, query.page())
                        && Objects.equals(expectedPerPage, query.perPage())
                        && Objects.equals(expectedTerms, query.terms())
                        && Objects.equals(expectedSort, query.sort())
                        && Objects.equals(expectedDirection, query.direction())
        ));
    }
}
