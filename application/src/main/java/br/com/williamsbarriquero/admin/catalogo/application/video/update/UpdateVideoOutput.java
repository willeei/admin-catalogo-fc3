package br.com.williamsbarriquero.admin.catalogo.application.video.update;

import br.com.williamsbarriquero.admin.catalogo.domain.video.Video;

public record UpdateVideoOutput(String id) {

    public static UpdateVideoOutput from(final Video aVideo) {
        return new UpdateVideoOutput(aVideo.getId().getValue());
    }
}
