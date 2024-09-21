package tech.willeei.admin.catalogo.domain.video;

import tech.willeei.admin.catalogo.domain.event.DomainEvent;
import tech.willeei.admin.catalogo.domain.utils.InstantUtils;

import java.time.Instant;

public record VideoMediaCreated(
        String resourceId,
        String filePath,
        Instant occurredOn
) implements DomainEvent {

    public VideoMediaCreated(final String resourceId, final String filePath) {
        this(resourceId, filePath, InstantUtils.now());
    }
}