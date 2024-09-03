package br.com.williamsbarriquero.admin.catalogo.application.video.delete;

import java.util.Objects;

import br.com.williamsbarriquero.admin.catalogo.domain.video.MediaResourceGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.video.VideoGateway;
import br.com.williamsbarriquero.admin.catalogo.domain.video.VideoID;

public class DefaultDeleteVideoUseCase extends DeleteVideoUseCase {

    private final VideoGateway videoGateway;
    private final MediaResourceGateway mediaResourceGateway;

    public DefaultDeleteVideoUseCase(
            final VideoGateway videoGateway,
            final MediaResourceGateway mediaResourceGateway
    ) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public void execute(final String anIn) {
        final var aVideoId = VideoID.from(anIn);
        this.videoGateway.deleteById(aVideoId);
        this.mediaResourceGateway.clearResources(aVideoId);
    }
}
