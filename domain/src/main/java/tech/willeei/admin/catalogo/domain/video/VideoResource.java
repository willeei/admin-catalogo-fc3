package tech.willeei.admin.catalogo.domain.video;

import tech.willeei.admin.catalogo.domain.resource.Resource;

public record VideoResource(
        VideoMediaType type,
        Resource resource
) {

    public static VideoResource with(final VideoMediaType type, final Resource resource) {
        return new VideoResource(type, resource);
    }
}
