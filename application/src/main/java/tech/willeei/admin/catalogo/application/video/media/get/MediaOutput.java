package tech.willeei.admin.catalogo.application.video.media.get;

import tech.willeei.admin.catalogo.domain.resource.Resource;

public record MediaOutput(
        byte[] content,
        String contentType,
        String name
) {

    public static MediaOutput with(final Resource aResource) {
        return new MediaOutput(aResource.content(), aResource.contentType(), aResource.name());
    }
}
