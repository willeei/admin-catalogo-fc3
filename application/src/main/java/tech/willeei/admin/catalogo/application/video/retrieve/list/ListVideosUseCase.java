package tech.willeei.admin.catalogo.application.video.retrieve.list;

import tech.willeei.admin.catalogo.application.UseCase;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.video.VideoSearchQuery;

public abstract class ListVideosUseCase
        extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
