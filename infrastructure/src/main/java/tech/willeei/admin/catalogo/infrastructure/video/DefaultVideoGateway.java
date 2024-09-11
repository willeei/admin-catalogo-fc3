package tech.willeei.admin.catalogo.infrastructure.video;

import static tech.willeei.admin.catalogo.domain.utils.CollectionUtils.mapTo;
import static tech.willeei.admin.catalogo.domain.utils.CollectionUtils.nullIfEmpty;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tech.willeei.admin.catalogo.domain.Identifier;
import tech.willeei.admin.catalogo.domain.pagination.Pagination;
import tech.willeei.admin.catalogo.domain.video.Video;
import tech.willeei.admin.catalogo.domain.video.VideoGateway;
import tech.willeei.admin.catalogo.domain.video.VideoID;
import tech.willeei.admin.catalogo.domain.video.VideoPreview;
import tech.willeei.admin.catalogo.domain.video.VideoSearchQuery;
import tech.willeei.admin.catalogo.infrastructure.utils.SqlUtils;
import tech.willeei.admin.catalogo.infrastructure.video.persistence.VideoJpaEntity;
import tech.willeei.admin.catalogo.infrastructure.video.persistence.VideoRepository;

@Component
public class DefaultVideoGateway implements VideoGateway {

    private final VideoRepository videoRepository;

    public DefaultVideoGateway(final VideoRepository videoRepository) {
        this.videoRepository = Objects.requireNonNull(videoRepository);
    }

    @Override
    @Transactional
    public Video create(final Video aVideo) {
        return save(aVideo);
    }

    @Override
    public void deleteById(final VideoID anId) {
        final var anVideoId = anId.getValue();
        if (this.videoRepository.existsById(anVideoId)) {
            this.videoRepository.deleteById(anVideoId);
        }
    }

    @Override
    public Pagination<VideoPreview> findAll(final VideoSearchQuery aQuery) {
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        final var actualPage = this.videoRepository.findAll(
                SqlUtils.like(SqlUtils.upper(aQuery.terms())),
                nullIfEmpty(mapTo(aQuery.castMembers(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.categories(), Identifier::getValue)),
                nullIfEmpty(mapTo(aQuery.genres(), Identifier::getValue)),
                page
        );

        return new Pagination<>(
                actualPage.getNumber(),
                actualPage.getSize(),
                actualPage.getTotalElements(),
                actualPage.toList()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Video> findById(final VideoID anId) {
        return this.videoRepository.findById(anId.getValue())
                .map(VideoJpaEntity::toAggregate);
    }

    @Override
    @Transactional
    public Video update(Video aVideo) {
        return save(aVideo);
    }

    private Video save(final Video aVideo) {
        return this.videoRepository.save(VideoJpaEntity.from(aVideo)).toAggregate();
    }
}
