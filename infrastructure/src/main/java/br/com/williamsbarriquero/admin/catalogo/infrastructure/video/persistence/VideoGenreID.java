package br.com.williamsbarriquero.admin.catalogo.infrastructure.video.persistence;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VideoGenreID implements Serializable {

    @Column(name = "video_id", nullable = false)
    private UUID videoId;

    @Column(name = "genre_id", nullable = false)
    private UUID genreId;

    public VideoGenreID() {
    }

    private VideoGenreID(final UUID videoId, final UUID genreId) {
        this.videoId = videoId;
        this.genreId = genreId;
    }

    public static VideoGenreID from(final UUID videoId, final UUID genreId) {
        return new VideoGenreID(videoId, genreId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VideoGenreID that = (VideoGenreID) o;
        return Objects.equals(videoId, that.videoId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(videoId, genreId);
    }

    public UUID getVideoId() {
        return videoId;
    }

    public VideoGenreID setVideoId(UUID videoId) {
        this.videoId = videoId;
        return this;
    }

    public UUID getGenreId() {
        return genreId;
    }

    public VideoGenreID setGenreId(UUID genreId) {
        this.genreId = genreId;
        return this;
    }
}
