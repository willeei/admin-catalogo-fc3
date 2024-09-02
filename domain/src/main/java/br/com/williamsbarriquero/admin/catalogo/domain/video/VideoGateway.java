package br.com.williamsbarriquero.admin.catalogo.domain.video;

import java.util.Optional;

import br.com.williamsbarriquero.admin.catalogo.domain.pagination.Pagination;

public interface VideoGateway {

    Video create(Video aVideo);

    void deleteById(VideoID anId);

    Optional<Video> findById(VideoID anId);

    Video update(Video aVideo);

    Pagination<VideoPreview> findAll(VideoSearchQuery aQuery);
}
