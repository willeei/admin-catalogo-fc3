package br.com.williamsbarriquero.admin.catalogo.infrastructure.video.persistence;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VideoGenreID implements Serializable {

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @Column(name = "genre_id", nullable = false)
    private String genreId;

    public VideoGenreID() {
    }

    private VideoGenreID(final String videoId, final String genreId) {
        this.videoId = videoId;
        this.genreId = genreId;
    }

    public static VideoGenreID from(final String videoId, final String genreId) {
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

    public String getVideoId() {
        return videoId;
    }

    public VideoGenreID setVideoId(String videoId) {
        this.videoId = videoId;
        return this;
    }

    public String getGenreId() {
        return genreId;
    }

    public VideoGenreID setGenreId(String genreId) {
        this.genreId = genreId;
        return this;
    }
}
