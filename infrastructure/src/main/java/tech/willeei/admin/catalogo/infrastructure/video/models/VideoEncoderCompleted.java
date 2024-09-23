package tech.willeei.admin.catalogo.infrastructure.video.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("COMPLETED")
public record VideoEncoderCompleted(
        @JsonProperty("id") String id,
        @JsonProperty("output_bucket_path") String outputBucket,
        @JsonProperty("video") VideoMetadata video
) implements VideoEncoderResult {

    public static final String COMPLETED = "COMPLETED";

    @Override
    public String getStatus() {
        return COMPLETED;
    }
}
