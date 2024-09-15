package tech.willeei.admin.catalogo.infrastructure.services.local;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.willeei.admin.catalogo.domain.Fixture;
import tech.willeei.admin.catalogo.domain.utils.IdUtils;
import tech.willeei.admin.catalogo.domain.video.VideoMediaType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class InMemoryStorageServiceTest {

    private final InMemoryStorageService target = new InMemoryStorageService();

    @BeforeEach
    public void setUp() {
        target.clear();
    }

    @Test
    void givenValidResource_whenCallsStore_shouldStoreIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        // when
        target.store(expectedName, expectedResource);

        // then
        Assertions.assertEquals(expectedResource, target.storage().get(expectedName));
    }

    @Test
    void givenResource_whenCallsGet_shouldRetrieveIt() {
        // given
        final var expectedName = IdUtils.uuid();
        final var expectedResource = Fixture.Videos.resource(VideoMediaType.VIDEO);

        target.storage().put(expectedName, expectedResource);

        // when
        final var actualResource = target.get(expectedName).get();

        // then
        Assertions.assertEquals(expectedResource, actualResource);
    }

    @Test
    void givenInvalidResource_whenCallsGet_shouldRetrieveEmpty() {
        // given
        final var expectedName = IdUtils.uuid();

        // when
        final var actualResource = target.get(expectedName);

        // then
        Assertions.assertTrue(actualResource.isEmpty());
    }

    @Test
    void givenPrefix_whenCallsList_shouldRetrieveAll() {
        // given
        final var expectedNames = List.of(
                "videos_" + IdUtils.uuid(),
                "videos_" + IdUtils.uuid(),
                "videos_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(expectedNames);
        all.add("images_" + IdUtils.uuid());
        all.add("images_" + IdUtils.uuid());

        all.forEach(name -> target.storage().put(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

        // when
        final var actualResource = target.list("videos_");

        // then
        Assertions.assertTrue(
                expectedNames.size() == actualResource.size()
                        && expectedNames.containsAll(actualResource)
        );
    }

    @Test
    void givenValidNames_whenCallsDelete_shouldDeleteAll() {
        // given
        final var videos = List.of(
                "videos_" + IdUtils.uuid(),
                "videos_" + IdUtils.uuid(),
                "videos_" + IdUtils.uuid()
        );
        
        final var expectedNames = Set.of(
                "images_" + IdUtils.uuid(),
                "images_" + IdUtils.uuid()
        );

        final var all = new ArrayList<>(videos);
        all.addAll(expectedNames);

        all.forEach(name -> target.storage().put(name, Fixture.Videos.resource(VideoMediaType.VIDEO)));

        Assertions.assertEquals(5, target.storage().size());

        // when
        target.deleteAll(videos);

        // then
        Assertions.assertEquals(2, target.storage().size());

        final var actualKeys = target.storage().keySet();
        Assertions.assertTrue(
                expectedNames.size() == actualKeys.size()
                        && expectedNames.containsAll(actualKeys)
        );
    }
}