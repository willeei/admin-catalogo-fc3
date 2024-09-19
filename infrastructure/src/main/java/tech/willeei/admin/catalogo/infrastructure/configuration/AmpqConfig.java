package tech.willeei.admin.catalogo.infrastructure.configuration;

import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoCreatedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoEncodedQueue;
import tech.willeei.admin.catalogo.infrastructure.configuration.annotations.VideoEvents;
import tech.willeei.admin.catalogo.infrastructure.configuration.properties.amqp.QueueProperties;

@Configuration
public class AmpqConfig {

    @Bean
    @ConfigurationProperties("amqp.queues.video-created")
    @VideoCreatedQueue
    public QueueProperties videoCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    @ConfigurationProperties("amqp.queues.video-encoded")
    @VideoEncodedQueue
    public QueueProperties encodedCreatedQueueProperties() {
        return new QueueProperties();
    }

    @Bean
    public String queueName(@VideoCreatedQueue QueueProperties props) {
        return props.getQueue();
    }

    @Configuration
    static class Admin {

        @Bean
        @VideoEvents
        public DirectExchange videoEventsExchange(@VideoCreatedQueue QueueProperties props) {
            return new DirectExchange(props.getExchange());
        }

        @Bean
        @VideoCreatedQueue
        public Queue videoCreatedQueue(@VideoCreatedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @VideoCreatedQueue
        public Binding videoCreatedBinding(
                @VideoEvents DirectExchange exchange,
                @VideoCreatedQueue Queue queue,
                @VideoCreatedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }

        @Bean
        @VideoEncodedQueue
        public Queue videoEncodedQueue(@VideoEncodedQueue QueueProperties props) {
            return new Queue(props.getQueue());
        }

        @Bean
        @VideoEncodedQueue
        public Binding videoEncodedBinding(
                @VideoEvents DirectExchange exchange,
                @VideoEncodedQueue Queue queue,
                @VideoEncodedQueue QueueProperties props
        ) {
            return BindingBuilder.bind(queue).to(exchange).with(props.getRoutingKey());
        }
    }
}
