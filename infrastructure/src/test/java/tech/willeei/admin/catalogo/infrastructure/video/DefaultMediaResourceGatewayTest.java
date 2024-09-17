package tech.willeei.admin.catalogo.infrastructure.video;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.willeei.admin.catalogo.IntegrationTest;
import tech.willeei.admin.catalogo.domain.video.*;
import tech.willeei.admin.catalogo.infrastructure.services.StorageService;
import tech.willeei.admin.catalogo.infrastructure.services.local.InMemoryStorageService;

import java.util.ArrayList;

import static tech.willeei.admin.catalogo.domain.Fixture.Videos.mediaType;
import static tech.willeei.admin.catalogo.domain.Fixture.Videos.resource;

@IntegrationTest
class DefaultMediaResourceGatewayTest {

    @Autowired
    private MediaResourceGateway mediaResourceGateway;

    @Autowired
    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService().clear();
    }

    @Test
    void testInjection() {
        Assertions.assertNotNull(mediaResourceGateway);
        Assertions.assertInstanceOf(DefaultMediaResourceGateway.class, mediaResourceGateway);

        Assertions.assertNotNull(storageService);
        Assertions.assertInstanceOf(InMemoryStorageService.class, storageService);
    }

    @Test
    void givenValidResource_whenCallsStorageAudioVideo_shouldStoreIt() {
        // given
        final var expectedVideoId = VideoID.unique();
        final var expectedType = VideoMediaType.VIDEO;
        final var expectedResource = resource(expectedType);
        final var expectedLocation =
                "videoId-%s/type-%s".formatted(expectedVideoId.getValue(), expectedType.name());
        final var expectedStatus = MediaStatus.PENDING;
        final var expectedEncodedLocation = "";

        // when
        final var actualMedia = this.mediaResourceGateway.storeAudioVideo(
                expectedVideoId, VideoResource.with(expectedType, expectedResource)
        );

        // then
        Assertions.assertNotNull(actualMedia.id());
        Assertions.assertEquals(expectedLocation, actualMedia.rawLocation());
        Assertions.assertEquals(expectedResource.name(), actualMedia.name());
        Assertions.assertEquals(expectedResource.checksum(), actualMedia.checksum());
        Assertions.assertEquals(expectedStatus, actualMedia.status());
        Assertions.assertEquals(expectedEncodedLocation, actualMedia.encodedLocation());

        final var actualStored = storageService().storage().get(expectedLocation);

        Assertions.assertEquals(expectedResource, actualStored);
    }

    @Test
    void givenValidResource_whenCallsStorageImage_shouldStoreIt() {
        // given
        final var expectedVideoId = VideoID.unique();
        final var expectedType = VideoMediaType.BANNER;
        final var expectedResource = resource(expectedType);
        final var expectedLocation =
                "videoId-%s/type-%s".formatted(expectedVideoId.getValue(), expectedType.name());

        // when
        final var actualMedia = this.mediaResourceGateway.storeImage(
                expectedVideoId, VideoResource.with(expectedType, expectedResource)
        );

        // then
        Assertions.assertNotNull(actualMedia.id());
        Assertions.assertEquals(expectedLocation, actualMedia.location());
        Assertions.assertEquals(expectedResource.name(), actualMedia.name());
        Assertions.assertEquals(expectedResource.checksum(), actualMedia.checksum());

        final var actualStored = storageService().storage().get(expectedLocation);

        Assertions.assertEquals(expectedResource, actualStored);
    }

    @Test
    void givenValidVideoId_whenCallsGetResource_shouldReturnIt() {
        // given
        final var videoOne = VideoID.unique();
        final var expectedType = VideoMediaType.VIDEO;
        final var expectedResource = resource(expectedType);

        storageService().store("videoId-%s/type-%s".formatted(videoOne.getValue(), expectedType), expectedResource);
        storageService().store(
                "videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.TRAILER.name()),
                resource(mediaType())
        );
        storageService().store(
                "videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.BANNER.name()),
                resource(mediaType())
        );

        Assertions.assertEquals(3, storageService().storage().size());

        // when
        final var actualResult = this.mediaResourceGateway.getResource(videoOne, expectedType).get();

        // then
        Assertions.assertEquals(expectedResource, actualResult);
    }

    @Test
    void givenInvalidType_whenCallsGetResource_shouldReturnEmpty() {
        // given
        final var videoOne = VideoID.unique();
        final var expectedType = VideoMediaType.THUMBNAIL;

        storageService().store(
                "videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.VIDEO.name()),
                resource(mediaType())
        );
        storageService().store(
                "videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.TRAILER.name()),
                resource(mediaType())
        );
        storageService().store(
                "videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.BANNER.name()),
                resource(mediaType())
        );

        Assertions.assertEquals(3, storageService().storage().size());

        // when
        final var actualResult = this.mediaResourceGateway.getResource(videoOne, expectedType);

        // then
        Assertions.assertTrue(actualResult.isEmpty());
    }

    @Test
    void givenValidVideoId_whenCallsClearResources_shouldDeleteAll() {
        // given
        final var videoOne = VideoID.unique();
        final var videoTwo = VideoID.unique();

        final var expectedValues = getValues(videoOne, videoTwo);

        Assertions.assertEquals(5, storageService().storage().size());

        // when
        this.mediaResourceGateway.clearResources(videoOne);

        // then
        Assertions.assertEquals(2, storageService().storage().size());

        final var actualKeys = storageService().storage().keySet();

        Assertions.assertTrue(
                expectedValues.size() == actualKeys.size() && actualKeys.containsAll(expectedValues)
        );
    }

    private ArrayList<String> getValues(VideoID videoOne, VideoID videoTwo) {
        final var toBeDeleted = new ArrayList<String>();
        toBeDeleted.add("videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.VIDEO.name()));
        toBeDeleted.add("videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.TRAILER.name()));
        toBeDeleted.add("videoId-%s/type-%s".formatted(videoOne.getValue(), VideoMediaType.BANNER.name()));

        final var expectedValues = new ArrayList<String>();
        expectedValues.add("videoId-%s/type-%s".formatted(videoTwo.getValue(), VideoMediaType.VIDEO.name()));
        expectedValues.add("videoId-%s/type-%s".formatted(videoTwo.getValue(), VideoMediaType.BANNER.name()));

        toBeDeleted.forEach(id -> storageService().store(id, resource(mediaType())));
        expectedValues.forEach(id -> storageService().store(id, resource(mediaType())));
        return expectedValues;
    }

    private InMemoryStorageService storageService() {
        return (InMemoryStorageService) storageService;
    }
}