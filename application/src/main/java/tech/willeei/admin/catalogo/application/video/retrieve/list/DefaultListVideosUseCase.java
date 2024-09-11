package tech.willeei.admin.catalogo.application.video.retrieve.list;

import java.util.Objects;

import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.video.VideoGateway;
import tech.willeei.admin.catalogo.domain.video.VideoSearchQuery;

public class DefaultListVideosUseCase extends ListVideosUseCase {

    private final VideoGateway videoGateway;

    public DefaultListVideosUseCase(final VideoGateway videoGateway) {
        this.videoGateway = Objects.requireNonNull(videoGateway);
    }

    @Override
    public Pagination<VideoListOutput> execute(final VideoSearchQuery aQuery) {
        return this.videoGateway.findAll(aQuery)
                .map(VideoListOutput::from);
    }
}
