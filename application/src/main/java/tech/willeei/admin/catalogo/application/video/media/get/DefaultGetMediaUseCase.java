package tech.willeei.admin.catalogo.application.video.media.get;

import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;
import tech.willeei.admin.catalogo.domain.validation.Error;
import tech.willeei.admin.catalogo.domain.video.MediaResourceGateway;
import tech.willeei.admin.catalogo.domain.video.VideoID;
import tech.willeei.admin.catalogo.domain.video.VideoMediaType;

import java.util.Objects;

public class DefaultGetMediaUseCase extends GetMediaUseCase {

    private final MediaResourceGateway mediaResourceGateway;

    public DefaultGetMediaUseCase(final MediaResourceGateway mediaResourceGateway) {
        this.mediaResourceGateway = Objects.requireNonNull(mediaResourceGateway);
    }

    @Override
    public MediaOutput execute(final GetMediaCommand aCmd) {
        final var anId = VideoID.from(aCmd.videoId());
        final var aType = VideoMediaType.of(
                aCmd.mediaType()).orElseThrow(() -> typeNotFound(aCmd.mediaType())
        );

        final var aResource = this.mediaResourceGateway.getResource(anId, aType)
                .orElseThrow(() -> nofFound(aCmd.videoId(), aCmd.mediaType()));
        return MediaOutput.with(aResource);
    }

    private NotFoundException nofFound(final String anId, final String aType) {
        return NotFoundException.with(new Error("Resource %s not found for video %s".formatted(aType, anId)));
    }

    private NotFoundException typeNotFound(final String aType) {
        return NotFoundException.with(new Error("Media type %s doesn't exists".formatted(aType)));
    }
}
