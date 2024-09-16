package tech.willeei.admin.catalogo.domain.resource;

import tech.willeei.admin.catalogo.domain.ValueObject;

import java.util.Arrays;
import java.util.Objects;

public class Resource extends ValueObject {

    private final byte[] content;
    private final String checksum;
    private final String contentType;
    private final String name;

    private Resource(final byte[] content, final String checksum, final String contentType, final String name) {
        this.content = Objects.requireNonNull(content);
        this.checksum = Objects.requireNonNull(checksum);
        this.contentType = Objects.requireNonNull(contentType);
        this.name = Objects.requireNonNull(name);
    }

    public static Resource with(
            final byte[] content,
            final String checksum,
            final String contentType,
            final String name
    ) {
        return new Resource(content, checksum, contentType, name);
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

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.deepEquals(content, resource.content)
                && Objects.equals(checksum, resource.checksum)
                && Objects.equals(contentType, resource.contentType)
                && Objects.equals(name, resource.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(content), checksum, contentType, name);
    }
}
