package tech.willeei.admin.catalogo.application.video.retrieve.list;

import java.time.Instant;

import tech.willeei.admin.catalogo.domain.video.Video;
import tech.willeei.admin.catalogo.domain.video.VideoPreview;

public record VideoListOutput(
        String id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt) {

    public static VideoListOutput from(final Video aVideo) {
        return new VideoListOutput(
                aVideo.getId().getValue(),
                aVideo.getTitle(),
                aVideo.getDescription(),
                aVideo.getCreatedAt(),
                aVideo.getUpdatedAt()
        );
    }

    public static VideoListOutput from(final VideoPreview aVideo) {
        return new VideoListOutput(
                aVideo.id(),
                aVideo.title(),
                aVideo.description(),
                aVideo.createdAt(),
                aVideo.updatedAt()
        );
    }
}
