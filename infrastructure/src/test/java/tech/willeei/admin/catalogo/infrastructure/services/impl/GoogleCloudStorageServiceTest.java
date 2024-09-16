package tech.willeei.admin.catalogo.infrastructure.services.impl;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import tech.willeei.admin.catalogo.domain.Fixture;
import tech.willeei.admin.catalogo.domain.resource.Resource;
import tech.willeei.admin.catalogo.domain.utils.IdUtils;
import tech.willeei.admin.catalogo.domain.video.VideoMediaType;

import java.util.List;

import static com.google.cloud.storage.Storage.BlobListOption.prefix;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GoogleCloudStorageServiceTest {

    private final String bucket = "fc2_test";
    private GoogleCloudStorageService target;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        this.storage = Mockito.mock(Storage.class);
        this.target = new GoogleCloudStorageService(this.bucket, this.storage);
    }

    @Test
    void givenValidResource_whenCallsStore_shouldPersistIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        final var blob = mockBlob(expectedName, expectedResource);
        doReturn(blob).when(this.storage).create(any(BlobInfo.class), any());

        // when
        this.target.store(expectedName, expectedResource);

        // then
        final var captor = ArgumentCaptor.forClass(BlobInfo.class);

        verify(storage, times(1)).create(captor.capture(), eq(expectedResource.content()));

        final var actualBlob = captor.getValue();
        Assertions.assertEquals(this.bucket, actualBlob.getBlobId().getBucket());
        Assertions.assertEquals(expectedName, actualBlob.getBlobId().getName());
        Assertions.assertEquals(expectedName, actualBlob.getName());
        Assertions.assertEquals(expectedResource.contentType(), actualBlob.getContentType());
        Assertions.assertEquals(expectedResource.checksum(), actualBlob.getCrc32cToHexString());
    }

    @Test
    void givenValidResource_whenCallsGet_shouldRetrieveIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        final var blob = mockBlob(expectedName, expectedResource);
        doReturn(blob).when(this.storage).get(anyString(), anyString());

        // when
        final var actualResource = this.target.get(expectedName).get();

        // then
        verify(storage, times(1)).get(eq(this.bucket), eq(expectedName));

        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    void givenInvalidResource_whenCallsGet_shouldRetrieveEmpty() {
        // given
        final var expectedName = IdUtils.uuid();

        doReturn(null).when(this.storage).get(anyString(), anyString());

        // when
        final var actualResource = this.target.get(expectedName);

        // then
        verify(storage, times(1)).get(eq(this.bucket), eq(expectedName));

        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    void givenPrefix_whenCallsList_shouldRetrieveAll() {
        // given
        final var expectedPrefix = "media_";

        final var expectedNameVideo = expectedPrefix + IdUtils.uuid();
        final var expectedVideo = Fixture.Videos.resource(VideoMediaType.VIDEO);

        final var expectedNameBanner = expectedPrefix + IdUtils.uuid();
        final var expectedBanner = Fixture.Videos.resource(VideoMediaType.BANNER);

        final var expectedResources = List.of(expectedNameVideo, expectedNameBanner);

        final var blobVideo = mockBlob(expectedNameVideo, expectedVideo);
        final var blobBanner = mockBlob(expectedNameBanner, expectedBanner);

        final var page = Mockito.mock(Page.class);
        doReturn(List.of(blobVideo, blobBanner)).when(page).iterateAll();

        doReturn(page).when(this.storage).list(anyString(), any());

        // when
        final var actualResource = this.target.list(expectedPrefix);

        // then
        verify(storage, times(1)).list(eq(this.bucket), eq(prefix(expectedPrefix)));

        Assertions.assertTrue(
                expectedResources.size() == actualResource.size()
                        && expectedResources.containsAll(actualResource)
        );
    }

    @Test
    void givenValidNames_whenCallsDelete_shouldDeleteAll() {
        // given
        final var expectedPrefix = "media_";

        final var expectedNameVideo = expectedPrefix + IdUtils.uuid();
        final var expectedNameBanner = expectedPrefix + IdUtils.uuid();

        final var expectedResources = List.of(expectedNameVideo, expectedNameBanner);

        // when
        this.target.deleteAll(expectedResources);

        // then
        final var captor = ArgumentCaptor.forClass(List.class);

        verify(storage, times(1)).delete(captor.capture());

        final var actualResources = ((List<BlobId>) captor.getValue()).stream()
                .map(BlobId::getName)
                .toList();

        Assertions.assertTrue(
                expectedResources.size() == actualResources.size()
                        && expectedResources.containsAll(actualResources)
        );
    }

    private Blob mockBlob(final String name, final Resource expectedResource) {
        final var blob = Mockito.mock(Blob.class);
        when(blob.getBlobId()).thenReturn(BlobId.of(this.bucket, name));
        when(blob.getCrc32cToHexString()).thenReturn(expectedResource.checksum());
        when(blob.getContent()).thenReturn(expectedResource.content());
        when(blob.getContentType()).thenReturn(expectedResource.contentType());
        when(blob.getName()).thenReturn(expectedResource.name());

        return blob;
    }
}