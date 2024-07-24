package br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateGenreRequest(
        @JsonProperty("name") String name,
        @JsonProperty("category_id") List<String> categories,
        @JsonProperty("is_active") Boolean active
) {

}
