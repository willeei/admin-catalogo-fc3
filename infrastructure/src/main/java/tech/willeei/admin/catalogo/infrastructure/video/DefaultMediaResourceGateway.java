package tech.willeei.admin.catalogo.infrastructure.video;

import org.springframework.stereotype.Component;
import tech.willeei.admin.catalogo.domain.resource.Resource;
import tech.willeei.admin.catalogo.domain.video.*;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.storage.StorageProperties;
import tech.willeei.admin.catalogo.infrastructure.services.StorageService;

import java.util.Optional;

@Component
public class DefaultMediaResourceGateway implements MediaResourceGateway {

    private final String filenamePattern;
    private final String locationPattern;
    private final StorageService storageService;

    public DefaultMediaResourceGateway(final StorageProperties props, final StorageService storageService) {
        this.filenamePattern = props.getFilenamePattern();
        this.locationPattern = props.getLocationPattern();
        this.storageService = storageService;
    }

    @Override
    public AudioVideoMedia storeAudioVideo(VideoID anId, VideoResource videoResource) {
        final var filepath = filepath(anId, videoResource);
        final var aResource = videoResource.resource();
        store(filepath, aResource);
        return AudioVideoMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public ImageMedia storeImage(VideoID anId, VideoResource videoResource) {
        final var filepath = filepath(anId, videoResource);
        final var aResource = videoResource.resource();
        store(filepath, aResource);
        return ImageMedia.with(aResource.checksum(), aResource.name(), filepath);
    }

    @Override
    public void clearResources(VideoID anId) {
        final var ids = this.storageService.list(folder(anId));
        this.storageService.deleteAll(ids);
    }

    @Override
    public Optional<Resource> getResource(VideoID anId, VideoMediaType type) {
        return Optional.empty();
    }

    private String filename(final VideoMediaType aType) {
        return filenamePattern.replace("{type}", aType.name());
    }

    private String folder(final VideoID anId) {
        return locationPattern.replace("{videoId}", anId.getValue());
    }

    private String filepath(VideoID anId, VideoResource aResource) {
        return folder(anId)
                .concat("/")
                .concat(filename(aResource.type()));
    }

    private void store(final String filepath, final Resource aResource) {
        this.storageService.store(filepath, aResource);
    }
}
