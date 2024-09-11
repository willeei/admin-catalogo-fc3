package tech.willeei.admin.catalogo.infrastructure.video.persistence;

import javax.persistence.*;

import tech.willeei.admin.catalogo.domain.video.ImageMedia;

@Entity(name = "ImageMedia")
@Table(name = "videos_image_media")
public class ImageMediaJpaEntity {

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    public ImageMediaJpaEntity() {
    }

    private ImageMediaJpaEntity(
            final String id,
            final String name,
            final String filePath
    ) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
    }

    public static ImageMediaJpaEntity from(final ImageMedia media) {
        return new ImageMediaJpaEntity(
                media.id(),
                media.name(),
                media.location()
        );
    }

    public ImageMedia toDomain() {
        return ImageMedia.with(
                getId(),
                getName(),
                getFilePath()
        );
    }

    public String getId() {
        return id;
    }

    public ImageMediaJpaEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ImageMediaJpaEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public ImageMediaJpaEntity setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}
