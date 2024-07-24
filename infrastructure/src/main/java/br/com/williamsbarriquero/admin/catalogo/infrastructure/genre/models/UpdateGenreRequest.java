package br.com.williamsbarriquero.admin.catalogo.infrastructure.genre.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record UpdateGenreRequest(
        @JsonProperty("name") String name,
        @JsonProperty("category_id") List<String> categories,
        @JsonProperty("is_active") Boolean active
) {

}
