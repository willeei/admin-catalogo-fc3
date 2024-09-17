package tech.willeei.admin.catalogo.application.video.media.get;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import tech.willeei.admin.catalogo.application.UseCaseTest;
import tech.willeei.admin.catalogo.domain.Fixture;
import tech.willeei.admin.catalogo.domain.exceptions.NotFoundException;
import tech.willeei.admin.catalogo.domain.video.MediaResourceGateway;
import tech.willeei.admin.catalogo.domain.video.VideoID;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

class GetMediaUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultGetMediaUseCase useCase;

    @Mock
    private MediaResourceGateway mediaResourceGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(mediaResourceGateway);
    }

    @Test
    void givenVideoIdAndType_whenIsValidCmd_shouldReturnResource() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedType = Fixture.Videos.mediaType();
        final var expectedResource = Fixture.Videos.resource(expectedType);

        when(mediaResourceGateway.getResource(expectedId, expectedType))
                .thenReturn(Optional.of(expectedResource));

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), expectedType.name());

        // when
        final var actualResult = this.useCase.execute(aCmd);

        // then
        Assertions.assertEquals(expectedResource.name(), actualResult.name());
        Assertions.assertEquals(expectedResource.content(), actualResult.content());
        Assertions.assertEquals(expectedResource.contentType(), actualResult.contentType());
    }

    @Test
    void givenVideoIdAndType_whenIsNotFound_shouldReturnNotFoundException() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedType = Fixture.Videos.mediaType();

        final var expectedMessage =
                "Resource %s not found for video %s".formatted(expectedType, expectedId.getValue());

        when(mediaResourceGateway.getResource(expectedId, expectedType))
                .thenReturn(Optional.empty());

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), expectedType.name());

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> this.useCase.execute(aCmd)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    void givenVideoIdAndType_whenTypeDoesntExists_shouldReturnNotFoundException() {
        // given
        final var expectedId = VideoID.unique();
        final var expectedMessage = "Media type QUALQUER doesn't exists";

        final var aCmd = GetMediaCommand.with(expectedId.getValue(), "QUALQUER");

        // when
        final var actualException = Assertions.assertThrows(
                NotFoundException.class, () -> this.useCase.execute(aCmd)
        );

        // then
        Assertions.assertEquals(expectedMessage, actualException.getMessage());
    }
}
