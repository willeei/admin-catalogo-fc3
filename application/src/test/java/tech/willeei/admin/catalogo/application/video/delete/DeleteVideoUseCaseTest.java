package tech.willeei.admin.catalogo.application.video.delete;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import tech.willeei.admin.catalogo.application.UseCaseTest;
import tech.willeei.admin.catalogo.domain.exceptions.InternalErrorException;
import tech.willeei.admin.catalogo.domain.video.MediaResourceGateway;
import tech.willeei.admin.catalogo.domain.video.VideoGateway;
import tech.willeei.admin.catalogo.domain.video.VideoID;

class DeleteVideoUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultDeleteVideoUseCase useCase;

    @Mock
    private VideoGateway videoGateway;

    @Mock
    private MediaResourceGateway mediaResourceGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(videoGateway, mediaResourceGateway);
    }

    @Test
    void givenAValidId_whenCallsDeleteVideo_shouldDeleteIt() {
        // given
        final var expectedId = VideoID.unique();

        doNothing()
                .when(videoGateway).deleteById(any());

        doNothing()
                .when(mediaResourceGateway).clearResources(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
        verify(mediaResourceGateway).clearResources(eq(expectedId));
    }

    @Test
    void givenAnInvalidId_whenCallsDeleteVideo_shouldBeOk() {
        // given
        final var expectedId = VideoID.from("1231");

        doNothing()
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertDoesNotThrow(() -> this.useCase.execute(expectedId.getValue()));

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }

    @Test
    void givenAValidId_whenCallsDeleteVideoAndGatewayThrowsException_shouldReceiveException() {
        // given
        final var expectedId = VideoID.from("1231");

        doThrow(InternalErrorException.with("Error on delete video", new RuntimeException()))
                .when(videoGateway).deleteById(any());

        // when
        Assertions.assertThrows(
                InternalErrorException.class,
                () -> this.useCase.execute(expectedId.getValue())
        );

        // then
        verify(videoGateway).deleteById(eq(expectedId));
    }
}
