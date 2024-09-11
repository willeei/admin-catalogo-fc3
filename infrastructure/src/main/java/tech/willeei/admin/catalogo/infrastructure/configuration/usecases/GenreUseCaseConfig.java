package tech.willeei.admin.catalogo.infrastructure.configuration.usecases;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tech.willeei.admin.catalogo.application.genre.create.CreateGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.create.DefaultCreateGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.delete.DefaultDeleteGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.delete.DeleteGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.retrieve.get.DefaultGetGenreByIdUseCase;
import tech.willeei.admin.catalogo.application.genre.retrieve.get.GetGenreByIdUseCase;
import tech.willeei.admin.catalogo.application.genre.retrieve.list.DefaultListGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.retrieve.list.ListGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.update.DefaultUpdateGenreUseCase;
import tech.willeei.admin.catalogo.application.genre.update.UpdateGenreUseCase;
import tech.willeei.admin.catalogo.domain.category.CategoryGateway;
import tech.willeei.admin.catalogo.domain.genre.GenreGateway;

@Configuration
public class GenreUseCaseConfig {

    private final CategoryGateway categoryGateway;
    private final GenreGateway genreGateway;

    public GenreUseCaseConfig(
            final CategoryGateway categoryGateway,
            final GenreGateway genreGateway
    ) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
        this.genreGateway = Objects.requireNonNull(genreGateway);
    }

    @Bean
    public CreateGenreUseCase createGenreUseCase() {
        return new DefaultCreateGenreUseCase(categoryGateway, genreGateway);
    }

    @Bean
    public DeleteGenreUseCase deleteGenreUseCase() {
        return new DefaultDeleteGenreUseCase(genreGateway);
    }

    @Bean
    public GetGenreByIdUseCase getGenreByIdUseCase() {
        return new DefaultGetGenreByIdUseCase(genreGateway);
    }

    @Bean
    public ListGenreUseCase listGenreUseCase() {
        return new DefaultListGenreUseCase(genreGateway);
    }

    @Bean
    public UpdateGenreUseCase updateGenreUseCase() {
        return new DefaultUpdateGenreUseCase(categoryGateway, genreGateway);
    }
}
