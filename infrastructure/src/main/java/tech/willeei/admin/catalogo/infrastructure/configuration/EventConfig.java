package tech.willeei.admin.catalogo.infrastructure.configuration;

import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.amqp.QueueProperties;
import tech.willeei.admin.catalogo.infrastructure.services.EventService;
import tech.willeei.admin.catalogo.infrastructure.services.impl.RabbitEventService;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    EventService videoCreatedEventService(@VideoCreatedQueue final QueueProperties props, final RabbitOperations ops) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
