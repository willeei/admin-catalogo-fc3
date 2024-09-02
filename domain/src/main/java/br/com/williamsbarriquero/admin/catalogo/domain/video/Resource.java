package br.com.williamsbarriquero.admin.catalogo.domain.video;

import java.util.Objects;

import br.com.williamsbarriquero.admin.catalogo.domain.ValueObject;

public class Resource extends ValueObject {

    private final byte[] content;
    private final String checksum;
    private final String contentType;
    private final Type type;

    private Resource(final byte[] content, final String checksum, final String contentType, final Type type) {
        this.content = Objects.requireNonNull(content);
        this.checksum = Objects.requireNonNull(checksum);
        this.contentType = Objects.requireNonNull(contentType);
        this.type = Objects.requireNonNull(type);
    }

    public static Resource with(
            final byte[] content,
            final String checksum,
            final String contentType,
            final Type type
    ) {
        return new Resource(content, checksum, contentType, type);
    }

    public String checksum() {
        return checksum;
    }

    public byte[] content() {
        return content;
    }

    public String contentType() {
        return contentType;
    }

    public Type type() {
        return type;
    }

    public enum Type {
        VIDEO,
        TRAILER,
        BANNER,
        THUMBNAIL,
        THUMBNAIL_HALF
    }
}
