package tech.willeei.admin.catalogo.infrastructure.configuration.usecases;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.willeei.admin.catalogo.application.video.media.update.DefaultUpdateMediaStatusUseCase;
import tech.willeei.admin.catalogo.application.video.media.update.UpdateMediaStatusUseCase;
import tech.willeei.admin.catalogo.domain.video.VideoGateway;

import java.util.Objects;

@Configuration
public class VideoUseCaseConfig {

    private final VideoGateway videoGateway;

    public VideoUseCaseConfig(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Bean
    public UpdateMediaStatusUseCase updateMediaStatusUseCase() {
        return new DefaultUpdateMediaStatusUseCase(this.videoGateway);
    }
}
