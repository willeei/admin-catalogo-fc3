package br.com.williamsbarriquero.admin.catalogo.application.video.retrieve.list;

import br.com.williamsbarriquero.admin.catalogo.application.UseCase;
import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;
import br.com.williamsbarriquero.admin.catalogo.domain.video.VideoSearchQuery;

public abstract class ListVideosUseCase
        extends UseCase<VideoSearchQuery, Pagination<VideoListOutput>> {
}
