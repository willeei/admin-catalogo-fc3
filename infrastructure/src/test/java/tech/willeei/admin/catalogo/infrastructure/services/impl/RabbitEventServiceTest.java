package tech.willeei.admin.catalogo.infrastructure.services.impl;

import org.junit.jupiter.api.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import tech.willeei.admin.catalogo.AmqpTest;
import tech.willeei.admin.catalogo.domain.video.VideoMediaCreated;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.json.Json;
import tech.willeei.admin.catalogo.infrastructure.services.EventService;

import java.util.concurrent.TimeUnit;

@AmqpTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RabbitEventServiceTest {

    private static final String LISTENER = "video.created";

    @Autowired
    @VideoCreatedQueue
    private EventService publisher;

    @Autowired
    private RabbitListenerTestHarness harness;

    @Test
    @Order(1)
    void shouldSendMessage() throws InterruptedException {
        // given
        final var notification = new VideoMediaCreated("resource", "filepath");

        final var expectedMessage = Json.writeValueAsString(notification);

        // when
        this.publisher.send(notification);

        // then
        final var invocationData =
                harness.getNextInvocationDataFor(LISTENER, 1, TimeUnit.SECONDS);

        Assertions.assertNotNull(invocationData);
        Assertions.assertNotNull(invocationData.getArguments());

        final var actualMessage = (String) invocationData.getArguments()[0];

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Component
    static class VideoCreatedNewsListener {

        @RabbitListener(id = LISTENER, queues = "${amqp.queues.video-created.routing-key}")
        void onVideoCreated(@Payload String message) {
            System.out.println(message);
        }
    }
}
